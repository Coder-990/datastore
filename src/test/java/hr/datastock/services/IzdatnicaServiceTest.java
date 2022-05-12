package hr.datastock.services;

import hr.datastock.MockEntityDataValues;
import hr.datastock.entities.IzdatnicaEntity;
import hr.datastock.repositories.IzdatnicaRepository;
import hr.datastock.services.impl.IzdatnicaServiceImpl;
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
class IzdatnicaServiceTest {


    @Mock
    private IzdatnicaRepository izdatnicaRepository;

    @InjectMocks
    private IzdatnicaServiceImpl izdatnicaService;

    @Nested
    @DisplayName("IzdatnicaService get all shipments")
    class IzdatnicaServiceTestGetAllShipments {

        @Test
        @DisplayName("GIVEN izdatnica records exists in database, WHEN all izdatnica records are requested, THEN all izdatnica records from database are returned.")
        void testGetAll() {
            final List<IzdatnicaEntity> expectedListOfIzdatnica = MockEntityDataValues.givenIzdatnicaDataRecords();

            when(izdatnicaRepository.findAll()).thenReturn(MockEntityDataValues.givenIzdatnicaDataRecords());
            final List<IzdatnicaEntity> actualListOfIzdatnica = izdatnicaService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfIzdatnica),
                    () -> assertEquals(expectedListOfIzdatnica, actualListOfIzdatnica)
            );
        }

        @Test
        @DisplayName("GIVEN there are no izdatnica records in database, WHEN all izdatnica records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<IzdatnicaEntity> expectedListOfIzdatnica = Collections.emptyList();

            when(izdatnicaRepository.findAll()).thenReturn(expectedListOfIzdatnica);
            final List<IzdatnicaEntity> actualListOfIzdatnica = izdatnicaService.getAll();

            assertAll(
                    () -> assertNotNull(actualListOfIzdatnica),
                    () -> assertEquals(expectedListOfIzdatnica, actualListOfIzdatnica)
            );
        }

    }

    @Nested
    @DisplayName("IzdatnicaService create")
    class IzdatnicaServiceTestCreateIzdatnica {
        @Test
        @DisplayName("GIVEN izdatnica record exists in database, WHEN a izdatnica record is updated, THEN izdatnica record is returned.")
        void testCreateIzdatnica() {
            final IzdatnicaEntity expectedIzdatnica = MockEntityDataValues.givenIzdatnicaDataRecords().get(0);

            when(izdatnicaRepository.save(any(IzdatnicaEntity.class))).thenReturn(expectedIzdatnica);
            final IzdatnicaEntity actualIzdatnica = izdatnicaService.createIzdatnica(expectedIzdatnica);

            assertAll(
                    () -> assertNotNull(actualIzdatnica),
                    () -> assertEquals(expectedIzdatnica, actualIzdatnica)
            );
        }
    }

    @Nested
    @DisplayName("IzdatnicaService delete izdatnica")
    class IzdatnicaServiceTestDeleteIzdatnica {
        @Test
        @DisplayName("GIVEN izdatnica record either exist or not, WHEN a single izdatnica record is deleted, THEN repository delete method should be called once.")
        void testDeleteIzdatnica() {
            final IzdatnicaEntity izdatnica = MockEntityDataValues.givenIzdatnicaDataRecords().get(1);

            izdatnicaRepository.delete(izdatnica);

            verify(izdatnicaRepository, times(1)).delete(izdatnica);
        }
    }
}