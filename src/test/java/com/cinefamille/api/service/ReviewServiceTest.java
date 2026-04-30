package com.cinefamille.api.service;

import com.cinefamille.api.dto.CreateReviewRequest;
import com.cinefamille.api.dto.ReviewDto;
import com.cinefamille.api.exception.ResourceNotFoundException;
import com.cinefamille.api.model.Movie;
import com.cinefamille.api.model.Review;
import com.cinefamille.api.model.User;
import com.cinefamille.api.repository.MovieRepository;
import com.cinefamille.api.repository.ReviewRepository;
import com.cinefamille.api.repository.UserRepository;
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

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    // Attention : ReviewService dépend de 3 repositories — il faut 3 @Mock
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ReviewService reviewService;

    // -------------------------------------------------------------------------
    // getAllReviews()
    // -------------------------------------------------------------------------

    @Test
    void getAllReviews_shouldReturnListOfDtos() {
        // ARRANGE
        // TODO : crée un User et un Movie (nécessaires pour construire une Review)
        User user = new User("username", "test@test.com", "password");

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Dune");
        movie.setGenre("Science-Fiction");
        movie.setYear(2021);

        // TODO : crée une Review avec new Review(user, movie, rating, comment, photoUrl)
        Review review = new Review(user, movie, 8, "Vraiment génial!", "photoUrl" );

        // TODO : dis au mock que reviewRepository.findAll() retourne List.of(cette review)
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // ACT
        // TODO : appelle reviewService.getAllReviews()
        List<ReviewDto> result = reviewService.getAllReviews();

        // ASSERT
        // TODO : vérifie que la liste a 1 élément
        assertThat(result).hasSize(1);

        // TODO : vérifie le rating du premier élément
        assertThat(result.get(0).getRating()).isEqualTo(8);
    }

    // -------------------------------------------------------------------------
    // getReviewById()
    // -------------------------------------------------------------------------

    @Test
    void getReviewById_shouldReturnDto_whenReviewExists() {
        // ARRANGE
        // TODO : crée un User, un Movie, une Review
        User user = new User("username", "test@test.com", "password");

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Dune");
        movie.setGenre("Science-Fiction");
        movie.setYear(2021);

        Review review = new Review(user, movie, 8, "Vraiment génial!", "photoUrl" );

        // TODO : dis au mock que reviewRepository.findById(1L) retourne Optional.of(cette review)
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // ACT
        // TODO : appelle reviewService.getReviewById(1L)
        ReviewDto result = reviewService.getReviewById(1L);

        // ASSERT
        // TODO : vérifie au moins un champ du DTO
        assertThat(result.getComment()).isEqualTo("Vraiment génial!");
    }

    @Test
    void getReviewById_shouldThrowException_whenReviewNotFound() {
        // ARRANGE
        // TODO : dis au mock que reviewRepository.findById(99L) retourne Optional.empty()
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT + ACT
        // TODO : vérifie qu'une ResourceNotFoundException est lancée avec message contenant "99"
        assertThatThrownBy(() -> reviewService.getReviewById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // -------------------------------------------------------------------------
    // createReview()
    // -------------------------------------------------------------------------

    @Test
    void createReview_shouldReturnDto_withCorrectFields() {
        // createReview() appelle les 3 repositories — configure bien les 3 mocks
        // ARRANGE
        // TODO : crée un User avec id=1L et un Movie avec id=2L
        User user = new User("username", "test@test.com", "password");
        user.setId(1L);

        Movie movie = new Movie();
        movie.setId(2L);
        movie.setTitle("Dune");
        movie.setGenre("Science-Fiction");
        movie.setYear(2021);

        // TODO : crée un CreateReviewRequest avec userId=1L, movieId=2L, rating=8, comment="Super film"
        CreateReviewRequest createReviewRequest = new CreateReviewRequest();
        createReviewRequest.setUserId(1L);
        createReviewRequest.setMovieId(2L);
        createReviewRequest.setRating(8);
        createReviewRequest.setComment("Super film");

        // TODO : dis au mock que userRepository.findById(1L) retourne Optional.of(user)
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // TODO : dis au mock que movieRepository.findById(2L) retourne Optional.of(movie)
        when(movieRepository.findById(2L)).thenReturn(Optional.of(movie));

        // TODO : crée une Review "saved" avec user, movie, rating=8, comment="Super film"
        Review saved = new Review(user, movie, 8, "Super film", "photoUrl");

        // TODO : dis au mock que reviewRepository.save(any()) retourne cette review
        when(reviewRepository.save(any())).thenReturn(saved);

        // ACT
        // TODO : appelle reviewService.createReview(request)
        ReviewDto result = reviewService.createReview(createReviewRequest);

        // ASSERT
        // TODO : vérifie le rating du DTO retourné
        assertThat(result.getRating()).isEqualTo(8);

        // TODO : vérifie le comment du DTO retourné
        assertThat(result.getComment()).isEqualTo("Super film");
    }

    // -------------------------------------------------------------------------
    // deleteReview()
    // -------------------------------------------------------------------------

    @Test
    void deleteReview_shouldCallRepositoryDeleteById() {
        // ACT
        // TODO : appelle reviewService.deleteReview(1L)
        reviewService.deleteReview(1L);

        // ASSERT
        // TODO : vérifie que reviewRepository.deleteById(1L) a bien été appelé
        verify(reviewRepository).deleteById(1L);
    }

    // -------------------------------------------------------------------------
    // getReviewsByMovie()
    // -------------------------------------------------------------------------

    @Test
    void getReviewsByMovie_shouldReturnReviews_whenMovieExists() {
        // getReviewsByMovie() appelle d'abord movieRepository.findById() puis reviewRepository.findByMovie()
        // ARRANGE
        // TODO : crée un Movie avec id=1L et un User
        User user = new User("username", "test@test.com", "password");
        user.setId(2L);

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Dune");
        movie.setGenre("Science-Fiction");
        movie.setYear(2021);

        // TODO : crée une Review avec ce movie et cet user
        Review review = new Review(user, movie, 8, "Vraiment génial!", "photoUrl" );

        // TODO : dis au mock que movieRepository.findById(1L) retourne Optional.of(movie)
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // TODO : dis au mock que reviewRepository.findByMovie(movie) retourne List.of(review)
        when(reviewRepository.findByMovie(movie)).thenReturn(List.of(review));

        // ACT
        // TODO : appelle reviewService.getReviewsByMovie(1L)
        List<ReviewDto> result = reviewService.getReviewsByMovie(1L);

        // ASSERT
        // TODO : vérifie que la liste a 1 élément
        assertThat(result).hasSize(1);
    }

    // -------------------------------------------------------------------------
    // getReviewsByUser()
    // -------------------------------------------------------------------------

    @Test
    void getReviewsByUser_shouldReturnReviews_whenUserExists() {
        // Même logique que getReviewsByMovie() mais avec userRepository
        // ARRANGE
        // TODO : crée un User avec id=1L et un Movie
        User user = new User("username", "test@test.com", "password");
        user.setId(1L);

        Movie movie = new Movie();
        movie.setId(2L);
        movie.setTitle("Dune");
        movie.setGenre("Science-Fiction");
        movie.setYear(2021);

        // TODO : crée une Review avec cet user et ce movie
        Review review = new Review(user, movie, 8, "Vraiment génial!", "photoUrl" );

        // TODO : dis au mock que userRepository.findById(1L) retourne Optional.of(user)
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // TODO : dis au mock que reviewRepository.findByUser(user) retourne List.of(review)
        when(reviewRepository.findByUser(user)).thenReturn(List.of(review));

        // ACT
        // TODO : appelle reviewService.getReviewsByUser(1L)
        List<ReviewDto> result = reviewService.getReviewsByUser(1L);

        // ASSERT
        // TODO : vérifie que la liste a 1 élément
        assertThat(result).hasSize(1);
    }
}