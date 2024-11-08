package hr.datastore.services;

import hr.datastore.MockEntityDataValues;
import hr.datastore.entities.RobaEntity;
import hr.datastore.exceptions.RobaEntityExistsRuntimeException;
import hr.datastore.exceptions.RobaEntityNotFoundRuntimeException;
import hr.datastore.repositories.RobaRepository;
import hr.datastore.services.impl.RobaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RobaServiceTest {

    @Mock
    private RobaRepository robaRepository;

    @InjectMocks
    private RobaServiceImpl robaService;

    @Nested
    @DisplayName("RobaService get all articles")
    class RobaServiceTestGetAllArticles {

        @Test
        @DisplayName("GIVEN roba records exists in database, WHEN all roba records are requested, THEN all roba records from database are returned.")
        void testGetAll() {
            final List<RobaEntity> expectedListOfRoba = MockEntityDataValues.givenRobaDataRecords();

            when(robaRepository.findAll()).thenReturn(MockEntityDataValues.givenRobaDataRecords());
            final List<RobaEntity> actualListOfRoba = robaService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfRoba),
                    () -> assertEquals(expectedListOfRoba, actualListOfRoba));
        }

        @Test
        @DisplayName("GIVEN there are no roba records in database, WHEN all roba records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<RobaEntity> expectedListOfRoba = Collections.emptyList();

            when(robaRepository.findAll()).thenReturn(Collections.emptyList());
            final List<RobaEntity> actualListOfRoba = robaService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfRoba),
                    () -> assertEquals(expectedListOfRoba, actualListOfRoba)
            );
        }
    }

    @Nested
    @DisplayName("RobaService get company by id")
    class RobaServiceTestGetOneById {

        @Test
        @DisplayName("GIVEN roba record exists in database, WHEN a single roba record is fetched, THEN the roba with requested ID is returned.")
        void testGetOneById() {
            final RobaEntity expectedRoba = MockEntityDataValues.givenRobaDataRecords().get(0);

            when(robaRepository.findById(1L))
                    .thenReturn(MockEntityDataValues.givenRobaDataRecords()
                            .stream().filter(robaEntity -> robaEntity.getIdRobe() == 1L).findFirst());

            final RobaEntity actualRoba = robaService.getOneById(1L);

            assertAll(
                    () -> assertNotNull(actualRoba),
                    () -> assertEquals(expectedRoba, actualRoba)
            );
        }

        @Test
        @DisplayName("GIVEN roba record does not exists in database, WHEN a single roba record is fetched, THEN error is thrown.")
        void testGetOneByIdNonExistingId() {
            Class<RobaEntityNotFoundRuntimeException> expectedExceptionClass = RobaEntityNotFoundRuntimeException.class;

            when(robaRepository.findById(459L)).thenThrow(new RobaEntityNotFoundRuntimeException(459L));
            Executable executable = () -> robaService.getOneById(459L);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("RobaService create article")
    class RobaServiceTestCreateArticle {

        @Test
        @DisplayName("GIVEN roba record does not exist in database, WHEN new roba record is created, THEN new record is created and returned.")
        void testCreateArticle() {
            final RobaEntity expectedRoba = MockEntityDataValues.givenRobaDataRecords().get(5);

            when(robaRepository.save(any(RobaEntity.class)))
                    .thenReturn(expectedRoba);
            when(robaRepository.checkIfExistingArticleNameIsInTableRoba(expectedRoba))
                    .thenReturn(Collections.emptyList());

            final RobaEntity actualRoba = robaService.createArticle(expectedRoba);

            assertAll(
                    () -> assertNotNull(actualRoba),
                    () -> assertEquals(expectedRoba, actualRoba)
            );
        }

        @Test
        @DisplayName("GIVEN roba record exists in database, WHEN new roba record is created, THEN error is thrown.")
        void testCreateRobaWithExistingNameRecord() {
            final RobaEntity expectedRoba = MockEntityDataValues.givenRobaDataRecords().get(3);
            Class<RobaEntityExistsRuntimeException> expectedExceptionClass = RobaEntityExistsRuntimeException.class;

            when(robaRepository.checkIfExistingArticleNameIsInTableRoba(expectedRoba))
                    .thenReturn(MockEntityDataValues.givenRobaDataRecords().subList(0, 4));

            Executable executable = () -> robaService.createArticle(expectedRoba);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("RobaService update article")
    class RobaServiceTestUpdateExistingArticle {

        @Test
        @DisplayName("GIVEN roba record exists in database, WHEN a roba record is updated, THEN roba record is updated and returned.")
        void testUpdateExistingArticle() {
            final RobaEntity existingRobaWithUpdates = MockEntityDataValues.givenRobaDataRecords().get(4);

            when(robaRepository.findById(9L))
                    .thenReturn(MockEntityDataValues.givenRobaDataRecords().stream().filter(robaEntity -> robaEntity.getIdRobe() == 9L).findFirst());
            when(robaRepository.save(any(RobaEntity.class)))
                    .thenReturn(existingRobaWithUpdates);
            when(robaRepository.checkIfExistingArticleNameIsInTableRoba(new RobaEntity(9L, "CPU_AMD_Rayzen7_1800X_BOX", 10, new BigDecimal("2589.99"), "Procesor AMD", "kom")))
                    .thenReturn(Collections.emptyList());

            final RobaEntity updatedRobaEntity = robaService.updateExistingArticle(existingRobaWithUpdates, 9L);

            assertAll(
                    () -> assertNotNull(updatedRobaEntity),
                    () -> assertEquals(existingRobaWithUpdates, updatedRobaEntity)
            );
        }

        @Test
        @DisplayName("GIVEN roba record does not exist in database, WHEN a roba record is updated, THEN error is thrown.")
        void testUpdateNonExistingFirma() {
            final RobaEntity existingRobaWithUpdates = MockEntityDataValues.givenRobaDataRecords().get(4);
            Class<RobaEntityNotFoundRuntimeException> expectedExceptionClass = RobaEntityNotFoundRuntimeException.class;

            when(robaRepository.findById(1L))
                    .thenThrow(expectedExceptionClass);
            Executable executable = () -> robaService.updateExistingArticle(existingRobaWithUpdates, 1L);

            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("RobaService delete article")
    class RobaServiceTestDeleteArticle {
        @Test
        @DisplayName("GIVEN roba record either exist or not, WHEN a single roba record is deleted, THEN repository roba method should be called once.")
        void testDeleteFirma() {
            final RobaEntity roba = MockEntityDataValues.givenRobaDataRecords().get(0);

            robaRepository.delete(roba);

            verify(robaRepository, times(1)).delete(roba);
        }
    }
}
