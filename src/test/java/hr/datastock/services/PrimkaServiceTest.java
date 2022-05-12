package hr.datastock.services;

import hr.datastock.MockEntityDataValues;
import hr.datastock.entities.PrimkaEntity;
import hr.datastock.repositories.PrimkaRepository;
import hr.datastock.services.impl.PrimkaServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrimkaServiceTest {

    @Mock
    private PrimkaRepository primkaRepository;

    @InjectMocks
    private PrimkaServiceImpl primkaService;

    @Nested
    @DisplayName("PrimkaService get all recipients")
    class PrimkaServiceTestGetAllCompanies {

        @Test
        @DisplayName("GIVEN primka records exists in database, WHEN all primka records are requested, THEN all primka records from database are returned.")
        void testGetAll() {
            final List<PrimkaEntity> expectedListOfPrimka = MockEntityDataValues.givenPrimkaDataRecords();

            when(primkaRepository.findAll()).thenReturn(MockEntityDataValues.givenPrimkaDataRecords());
            final List<PrimkaEntity> actualListOfPrimka = primkaService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfPrimka),
                    () -> assertEquals(expectedListOfPrimka, actualListOfPrimka)
            );
        }

        @Test
        @DisplayName("GIVEN there are no primka records in database, WHEN all primka records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<PrimkaEntity> expectedListOfPrimka = Collections.emptyList();

            when(primkaRepository.findAll()).thenReturn(Collections.emptyList());
            final List<PrimkaEntity> actualListOfPrimka = primkaService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfPrimka),
                    () -> assertEquals(expectedListOfPrimka, actualListOfPrimka)
            );
        }

    }

    @Nested
    @DisplayName("PrimkaService create primka")
    class PrimkaServiceTestCreatePrimka {

        @Test
        @DisplayName("GIVEN primka record exists in database, WHEN a primka record is updated, THEN primka record is updated and returned.")
        void testCreatePrimka() {
            final PrimkaEntity expectedListOfPrimka = MockEntityDataValues.givenPrimkaDataRecords().get(0);

            when(primkaRepository.save(any(PrimkaEntity.class))).thenReturn(expectedListOfPrimka);
            final PrimkaEntity actualListOfPrimka = primkaService.createPrimka(expectedListOfPrimka);

            assertAll(
                    () -> assertNotNull(actualListOfPrimka),
                    () -> assertEquals(expectedListOfPrimka, actualListOfPrimka)
            );
        }
    }

    @Nested
    @DisplayName("PrimkaService delete primka")
    class PrimkaServiceTestDeletePrimka {
        @Test
        @DisplayName("GIVEN primka record either exist or not, WHEN a single primka record is deleted, THEN repository delete method should be called once.")
        void testDeletePrimka() {
            final PrimkaEntity primka = MockEntityDataValues.givenPrimkaDataRecords().get(1);

            primkaRepository.delete(primka);

            verify(primkaRepository, times(1)).delete(primka);
        }
    }
}
