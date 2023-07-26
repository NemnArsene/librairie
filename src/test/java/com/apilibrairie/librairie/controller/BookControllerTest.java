package com.apilibrairie.librairie.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import com.apilibrairie.librairie.model.Book;

import com.apilibrairie.librairie.service.BookService;
import com.fasterxml.jackson.annotation.JsonInclude;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BookControllerTest {

    // On utilise simplement @Mock ici (plutôt que @MockBean) car @MockBean
    // s'applique uniquement aux
    // beans de Spring, alors que BookService est simplement une dépendance de
    // BookController
    @Mock
    private BookService bookService;

    // Annotation pour injecter les mocks dans le contrôleur
    @InjectMocks
    private BookController bookController;

    // Objet MockMvc pour tester les requêtes HTTP
    private MockMvc mockMvc;

    // Méthode d'initialisation exécutée avant chaque test
    @Before
    public void setup() {
        // Configuration de l'objet MockMvc avec le contrôleur à tester
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    /**
     * Test methode pour enregistrer un livre
     */
    @Test
    public void saveBookTest() throws Exception {
        Book book = new Book(null, "Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "978-31200-2718");

        // Utilisation de Mockito.when() plutôt que simplement when()
        Mockito.when(bookService.saveBook(Mockito.any(Book.class)))
                .thenReturn(new Book(null, "Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "978-31200-2718"));

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonBytes(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", nullValue())) // vérifie que l'ID est null
                .andExpect(jsonPath("$.title", is("Le Petit Prince")))
                .andExpect(jsonPath("$.author", is("Antoine de Saint-Exupéry")))
                .andExpect(jsonPath("$.publicationYear", is(1943)))
                .andExpect(jsonPath("$.isbn", is("978-31200-2718")));

    }

    private byte[] asJsonBytes(Book book) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(book);
    }

    /**
     * Test methode pour afficher tous les livres
     */
    @Test
    public void getAllBooksTest() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book(1L, "Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "978-31200-2718");
        Book book2 = new Book(2L, "Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997, "978-0747532743");
        Book book3 = new Book(3L, "To Kill a Mockingbird", "Harper Lee", 1960, "978-0446310789");
        books.add(book1);
        books.add(book2);
        books.add(book3);

        when(bookService.getAllBooks()).thenReturn(books);

        // Création d'un mock pour le service bookService
        BookService bookService = Mockito.mock(BookService.class);

        // Définition du comportement du mock pour la méthode getAllBooks()
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Le Petit Prince")))
                .andExpect(jsonPath("$[0].author", is("Antoine de Saint-Exupéry")))
                .andExpect(jsonPath("$[0].publicationYear", is(1943)))
                .andExpect(jsonPath("$[0].isbn", is("978-31200-2718")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Harry Potter and the Philosopher's Stone")))
                .andExpect(jsonPath("$[1].author", is("J.K. Rowling")))
                .andExpect(jsonPath("$[1].publicationYear", is(1997)))
                .andExpect(jsonPath("$[1].isbn", is("978-0747532743")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].title", is("To Kill a Mockingbird")))
                .andExpect(jsonPath("$[2].author", is("Harper Lee")))
                .andExpect(jsonPath("$[2].publicationYear", is(1960)))
                .andExpect(jsonPath("$[2].isbn", is("978-0446310789")));

    }

    public static String asJsonString(Object obj) throws Exception {
        // Création d'une instance de ObjectMapper pour convertir les objets en JSON et
        // exclusion des champs nulls
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Vérification si l'objet d'entrée est une instance de List
        if (obj instanceof List) {
            // Cast de l'objet en List
            List<?> listObj = (List<?>) obj;
            // Conversion de la liste en chaîne JSON et renvoi du résultat
            return objectMapper.writeValueAsString(listObj);
        } else {
            // Conversion de l'objet en chaîne JSON et renvoi du résultat
            return objectMapper.writeValueAsString(obj);
        }
    }

    /**
     * Test methode pour afficher un livre
     */
    @Test
    public void getBookByIdTest() throws Exception {
        // Création d'un mock pour le service bookService
        BookService bookService = Mockito.mock(BookService.class);

        // Création d'un objet Book à utiliser pour le test
        Book book1 = new Book(1L, "Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "978-31200-2718");
        when(bookService.getBookById(1L)).thenReturn(book1);

        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Le Petit Prince")))
                .andExpect(jsonPath("$.author", is("Antoine de Saint-Exupéry")))
                .andExpect(jsonPath("$.publicationYear", is(1943)))
                .andExpect(jsonPath("$.isbn", is("978-31200-2718")));
    }

    /**
     * Test methode de MAJ
     */
    @Test
    public void UpdateBookTest() throws Exception {
        // Définir les données de test

        // Création de l'objet book original
        Book originalBook = new Book();
        originalBook.setId(1L);
        originalBook.setTitle("Original Title");
        originalBook.setAuthor("Original Author");
        originalBook.setPublicationYear(2021);
        originalBook.setIsbn("Original Isbn");

        // Création des objets book et bookToUpdate
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPublicationYear(2022);
        updatedBook.setIsbn("Updated Isbn");

        // Configuration du mock pour la méthode updateBook()
        Mockito.when(bookService.updateBook(Mockito.any(Book.class), Mockito.eq(1L)))
                .thenReturn(updatedBook);

        // Appel de la méthode updateBook() du controller
        BookController bookController = new BookController(bookService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.author", is("Updated Author")))
                .andExpect(jsonPath("$.publicationYear", is(2022)))
                .andExpect(jsonPath("$.isbn", is("Updated Isbn")));

    }

    /**
     * Test methode de suppression
     */
    @Test
    public void testDeleteBook() {
        // Définition des paramètres de test
        long idToDelete = 1L;
        doAnswer(invocation -> {
            // Le code à exécuter lorsque la méthode deleteBook est appelée
            return null;
        }).when(bookService).deleteBook(anyLong());

        // Appel de la méthode à tester
        ResponseEntity<String> response = bookController.deleteBook(idToDelete);

        // Vérification des résultats
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book delete successfully!", response.getBody());
    }

}
