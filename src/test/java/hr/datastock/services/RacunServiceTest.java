package hr.datastock.services;

import hr.datastock.MockEntityDataValues;
import hr.datastock.entities.RacunEntity;
import hr.datastock.exceptions.RacunEntitiyNotFoundRuntimeException;
import hr.datastock.repositories.RacunRepository;
import hr.datastock.services.impl.RacunServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RacunServiceTest {

    @Mock
    private RacunRepository racunRepository;

    @InjectMocks
    private RacunServiceImpl racunService;

    @Nested
    @DisplayName("RacunService get user by id")
    class RacunServiceTestGetOneById {

        public static final String UNKNOWN = "unknown";
        public static final String USER = "user";

        @Test
        @DisplayName("GIVEN racun record exists in database, WHEN a single racun record is fetched, THEN the racun with requested ID is returned.")
        void testLoginGetOneById() {
            final Optional<RacunEntity> expectedRacun = MockEntityDataValues.givenRacunDataRecords().stream().findFirst();
            when(racunRepository.findById(USER))
                    .thenReturn(expectedRacun.stream().filter(racun -> racun.getUserId().equals(USER)).findFirst());

            Optional<RacunEntity> actualRacun = racunService.login(USER, USER);

            assertAll(
                    () -> assertNotNull(actualRacun),
                    () -> assertEquals(expectedRacun, actualRacun)
            );
        }

        @Test
        @DisplayName("GIVEN racun record does not exists in database, WHEN a single racun record is fetched, THEN error is thrown.")
        void testLoginGetOneByNonExistingId() {
            Class<RacunEntitiyNotFoundRuntimeException> expectedExceptionClass = RacunEntitiyNotFoundRuntimeException.class;

            when(racunRepository.findById(UNKNOWN)).thenThrow(new RacunEntitiyNotFoundRuntimeException(UNKNOWN));
            Executable executable = () -> racunService.login(UNKNOWN, UNKNOWN);

            assertThrows(expectedExceptionClass, executable);
        }


        @Nested
        @DisplayName("RacunService create user")
        class UserServiceTestCreateUser {
            @Test
            @DisplayName("GIVEN racun record exists in database, WHEN a single racun record is created, THEN new record is created and returned.")
            void testCreateAccount() {
                final RacunEntity expectedRacun = MockEntityDataValues.givenRacunDataRecords().getFirst();

                when(racunRepository.save(any(RacunEntity.class)))
                        .thenReturn(expectedRacun);

                final RacunEntity actualRacun = racunService.createAccount(expectedRacun);

                assertAll(
                        () -> assertNotNull(actualRacun),
                        () -> assertEquals(expectedRacun, actualRacun)
                );
            }
        }
    }
}