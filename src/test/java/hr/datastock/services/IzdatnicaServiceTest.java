package hr.datastock.services;

import hr.datastock.HardcodedDataValues;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IzdatnicaServiceTest {


    @Mock
    private IzdatnicaRepository izdatnicaRepository;

    @InjectMocks
    private IzdatnicaServiceImpl izdatnicaService;

    @Nested
    @DisplayName("IzdatnicaService get all shipments")
    class FirmeServiceTestAllCompanies {

        @Test
        void getAll() {
            final List<IzdatnicaEntity> expectedList = HardcodedDataValues.givenIzdatnicaDataRecords();
            final List<IzdatnicaEntity> actualList = izdatnicaService.getAll();

            when(izdatnicaRepository.findAll()).thenReturn(HardcodedDataValues.givenIzdatnicaDataRecords());

            assertAll(
                    () -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList)
            );
        }

        @Test
        void getAllEmpty() {
            final List<IzdatnicaEntity> expectedListOfIzdatnica = Collections.emptyList();
            final List<IzdatnicaEntity> actualListOfIzdatnica = izdatnicaService.getAll();

            when(izdatnicaRepository.findAll()).thenReturn(Collections.emptyList());

            assertAll(
                    () -> assertNotNull(actualListOfIzdatnica),
                    () -> assertEquals(expectedListOfIzdatnica, actualListOfIzdatnica)
            );
        }

    }

    @Nested
    @DisplayName("izdatnicaService create izdatnica")
    class FirmeServiceTestCreateFirma {

        @Test
        void createIzdatnica() {
            final IzdatnicaEntity expectedIzdatnica = HardcodedDataValues.givenIzdatnicaDataRecords().get(1);
            final IzdatnicaEntity actualIzdatnica = izdatnicaService.createIzdatnica(expectedIzdatnica);

            when(izdatnicaRepository.save(any(IzdatnicaEntity.class))).thenReturn(expectedIzdatnica);

            assertAll(
                    () -> assertNotNull(actualIzdatnica),
                    () -> assertEquals(expectedIzdatnica, actualIzdatnica)
            );
        }

    }


    @Test
    void deleteIzdatnica() {
    }
}