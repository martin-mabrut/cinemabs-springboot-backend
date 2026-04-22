package com.cinefamille.api.service;

import com.cinefamille.api.dto.CreateMovieRequest;
import com.cinefamille.api.dto.MovieDto;
import com.cinefamille.api.exception.ResourceNotFoundException;
import com.cinefamille.api.model.Movie;
import com.cinefamille.api.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class) dit à JUnit d'activer Mockito pour cette classe de test.
// Sans ça, les annotations @Mock et @InjectMocks ne fonctionnent pas.
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    // @Mock crée un faux MovieRepository — il ne touche pas à PostgreSQL.
    // On lui dira quoi retourner dans chaque test avec when(...).thenReturn(...)
    @Mock
    private MovieRepository movieRepository;

    // @InjectMocks crée une vraie instance de MovieService,
    // et y injecte automatiquement le @Mock ci-dessus à la place du vrai repository.
    @InjectMocks
    private MovieService movieService;

    // -------------------------------------------------------------------------
    // getAllMovies()
    // -------------------------------------------------------------------------

    // Exemple complet — lis-le attentivement avant de faire les suivants.
    @Test
    void getAllMovies_shouldReturnListOfDtos() {
        // ARRANGE — on prépare un faux film et on dit au mock quoi retourner
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setGenre("Science-Fiction");
        movie.setYear(2010);

        when(movieRepository.findAll()).thenReturn(List.of(movie));

        // ACT — on appelle la vraie méthode du service
        List<MovieDto> result = movieService.getAllMovies();

        // ASSERT — on vérifie le résultat
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Inception");
        assertThat(result.get(0).getGenre()).isEqualTo("Science-Fiction");
    }

    @Test
    void getAllMovies_shouldReturnEmptyList_whenNoMovies() {
        // ARRANGE
        // TODO : dis au mock de retourner une liste vide
        when(movieRepository.findAll()).thenReturn(List.of());

        // ACT
        // TODO : appelle movieService.getAllMovies() et stocke le résultat
        List<MovieDto> result = movieService.getAllMovies();

        // ASSERT
        // TODO : vérifie que le résultat est une liste vide (hint: isEmpty())
        assertThat(result).isEmpty();
    }

    // -------------------------------------------------------------------------
    // getMovieById()
    // -------------------------------------------------------------------------

    @Test
    void getMovieById_shouldReturnDto_whenMovieExists() {
        // ARRANGE
        // TODO : crée un Movie avec id=1L, title="Inception", genre="Science-Fiction", year=2010
        // TODO : dis au mock que findById(1L) retourne Optional.of(movie)
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setGenre("Science-Fiction");
        movie.setYear(2010);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // ACT
        // TODO : appelle movieService.getMovieById(1L) et stocke le résultat
        MovieDto result = movieService.getMovieById(1L);

        // ASSERT
        // TODO : vérifie que le titre du DTO retourné est bien "Inception"
        assertThat(result.getTitle()).isEqualTo("Inception");
    }

    @Test
    void getMovieById_shouldThrowException_whenMovieNotFound() {
        // ARRANGE
        // TODO : dis au mock que findById(99L) retourne Optional.empty()
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT + ACT — ici on utilise assertThatThrownBy pour vérifier qu'une exception est lancée
        // TODO : complète le TODO ci-dessous
        assertThatThrownBy(() -> movieService.getMovieById(99L))
                // TODO : vérifie que c'est bien une ResourceNotFoundException (hint: isInstanceOf())
                .isInstanceOf(ResourceNotFoundException.class)
                // TODO : vérifie que le message contient "99" (hint: hasMessageContaining())
                .hasMessageContaining("99")
                ;
    }

    // -------------------------------------------------------------------------
    // createMovie()
    // -------------------------------------------------------------------------

    @Test
    void createMovie_shouldReturnDto_withCorrectFields() {
        // ARRANGE
        // TODO : crée un CreateMovieRequest avec title="Dune", genre="Science-Fiction", year=2021
        CreateMovieRequest createMovieRequest = new CreateMovieRequest();
        createMovieRequest.setTitle("Dune");
        createMovieRequest.setGenre("Science-Fiction");
        createMovieRequest.setYear(2021);
        // TODO : crée un Movie "saved" avec les mêmes valeurs + id=1L
        //        (simule ce que retournerait movieRepository.save())
        Movie saved = new Movie();
        saved.setId(1L);
        saved.setTitle("Dune");
        saved.setGenre("Science-Fiction");
        saved.setYear(2021);
        // TODO : dis au mock que save(any()) retourne le movie "saved"
        //        (hint: when(movieRepository.save(any())).thenReturn(saved))
        when(movieRepository.save(any())).thenReturn(saved);

        // ACT
        // TODO : appelle movieService.createMovie(request) et stocke le résultat
        MovieDto result = movieService.createMovie(createMovieRequest);

        // ASSERT
        // TODO : vérifie que le titre du DTO est "Dune"
        assertThat(result.getTitle()).isEqualTo("Dune");
        // TODO : vérifie que l'id du DTO est 1L
        assertThat(result.getId()).isEqualTo(1L);
    }

    // -------------------------------------------------------------------------
    // deleteMovie()
    // -------------------------------------------------------------------------

    @Test
    void deleteMovie_shouldCallRepositoryDeleteById() {
        // ACT
        // TODO : appelle movieService.deleteMovie(1L)
        movieService.deleteMovie(1L);

        // ASSERT — ici on ne vérifie pas de valeur de retour (void),
        // on vérifie que le repository a bien été appelé avec le bon argument
        // TODO : complète la ligne ci-dessous
        verify(movieRepository).deleteById(1L);
    }

    // -------------------------------------------------------------------------
    // getMoviesByGenre()
    // -------------------------------------------------------------------------

    @Test
    void getMoviesByGenre_shouldReturnOnlyMatchingMovies() {
        // ARRANGE
        // TODO : crée un Movie avec genre="Action"
        Movie movie = new Movie();
        movie.setGenre("Action");
        // TODO : dis au mock que findByGenre("Action") retourne List.of(ce film)
        when(movieRepository.findByGenre("Action")).thenReturn(List.of(movie));

        // ACT
        // TODO : appelle movieService.getMoviesByGenre("Action")
        List<MovieDto> result = movieService.getMoviesByGenre("Action");

        // ASSERT
        // TODO : vérifie que la liste a 1 élément
        assertThat(result).hasSize(1);
        // TODO : vérifie que le genre du premier élément est "Action"
        assertThat(result.get(0).getGenre()).isEqualTo("Action");
    }
}