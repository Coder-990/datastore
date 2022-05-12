package hr.datastock.services;

import hr.datastock.MockEntityDataValues;
import hr.datastock.entities.StavkaIzdatniceEntity;
import hr.datastock.entities.StavkaPrimkeEntity;
import hr.datastock.repositories.StavkaPrimkeRepository;
import hr.datastock.services.impl.StavkaPrimkeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StavkaPrimkeServiceTest {

    @Mock
    private StavkaPrimkeRepository stavkaPrimkeRepository;

    @InjectMocks
    private StavkaPrimkeServiceImpl stavkaPrimkeService;

    @Nested
    @DisplayName("StavkaPrimkeService get all recipients")
    class StavkaPrimkeServiceTestGetAllItemRecipients {
        @Test
        @DisplayName("GIVEN stavkaPrimke records exists in database, WHEN all stavkaPrimke records are requested, THEN all stavkaPrimke records from database are returned.")
        void testGetAll() {
            final List<StavkaPrimkeEntity> expectedListOfStavkaPrimke = MockEntityDataValues.givenStavkaPrimkeDataRecords();

            when(stavkaPrimkeRepository.findAll()).thenReturn(expectedListOfStavkaPrimke);
            final List<StavkaPrimkeEntity> actualListOfStavkaPrimke = stavkaPrimkeService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfStavkaPrimke),
                    () -> assertEquals(expectedListOfStavkaPrimke, actualListOfStavkaPrimke)
            );
        }

        @Test
        @DisplayName("GIVEN there are no stavkaPrimke records in database, WHEN all stavkaPrimke records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<StavkaPrimkeEntity> expectedListOfStavkaPrimke = Collections.emptyList();

            when(stavkaPrimkeRepository.findAll()).thenReturn(expectedListOfStavkaPrimke);
            final List<StavkaPrimkeEntity> actualListOfStavkaPrimke = stavkaPrimkeService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfStavkaPrimke),
                    () -> assertEquals(expectedListOfStavkaPrimke, actualListOfStavkaPrimke)
            );
        }
    }

    @Nested
    @DisplayName("StavkaPrimkeService create recipient")
    class StavkaPrimkeServiceTestCreateItemRecipients {
        @Test
        @DisplayName("GIVEN there are no stavkaPrimke records in database, WHEN all stavkaPrimke records are requested, THEN empty list is returned.")
        void createStavkaPrimke() {
            final StavkaPrimkeEntity expectedStavkaPrimke = MockEntityDataValues.givenStavkaPrimkeDataRecords().get(0);

            when(stavkaPrimkeRepository.save(any(StavkaPrimkeEntity.class)))
                    .thenReturn(expectedStavkaPrimke);

            final StavkaPrimkeEntity actualStavkaIzdatnica = stavkaPrimkeService.createStavkaPrimke(expectedStavkaPrimke);

            assertAll(
                    () -> assertNotNull(actualStavkaIzdatnica),
                    () -> assertEquals(expectedStavkaPrimke, actualStavkaIzdatnica)
            );
        }
    }

    @Nested
    @DisplayName("StornoStavkaIzdatnicaService cancel stornoStavkaIzdatnice")
    class StavkaPrimkeServiceTestCancelItemRecipients {

        @Test
        @DisplayName("GIVEN stornoStavkaPrimke record exists in database, WHEN a stornoStavkaPrimke record is canceled/updated, THEN stornoStavkaPrimke record is canceled and returned.")
        void createStornoStavkePrimke() {
            final StavkaPrimkeEntity expectedStavkaPrimke = MockEntityDataValues.givenStavkaPrimkeDataRecords().get(0);

            when(stavkaPrimkeRepository.findById(4L))
                    .thenReturn(MockEntityDataValues.givenStavkaPrimkeDataRecords()
                            .stream()
                            .filter(stavkaPrimkeEntity -> stavkaPrimkeEntity.getIdStavkaPrimke() == 4L).findFirst());
            when(stavkaPrimkeRepository.save(any(StavkaPrimkeEntity.class)))
                    .thenReturn(expectedStavkaPrimke);

            final StavkaPrimkeEntity actualStavkaPrimke = stavkaPrimkeService.createStornoStavkePrimke(expectedStavkaPrimke).get();

            assertAll(
                    () -> assertNotNull(actualStavkaPrimke),
                    () -> assertEquals(expectedStavkaPrimke, actualStavkaPrimke)
            );
        }
    }
}