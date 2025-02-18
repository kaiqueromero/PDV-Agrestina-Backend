//package com.agrestina;
//
//import com.agrestina.domain.client.Client;
//import com.agrestina.dto.client.ClientRequestDTO;
//import com.agrestina.dto.client.ClientResponseDTO;
//import com.agrestina.repository.ClientRepository;
//import com.agrestina.service.ClientService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ClientServiceTest {
//
//    @InjectMocks
//    private ClientService clientService;
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRegisterClient() {
//        when(clientRepository.findByName(dto.name())).thenReturn(Optional.empty());
//
//        ClientResponseDTO response = clientService.register(dto);
//
//        assertNotNull(response);
//        assertEquals("John Doe", response.name());
//        verify(clientRepository, times(1)).save(any(Client.class));
//    }
//
//    @Test
//    void testRegisterClientAlreadyExists() {
//        ClientRequestDTO dto = new ClientRequestDTO("John Doe", "12345678901", Optional.empty(), "123 Main St", "555-1234", "john@example.com");
//        Client existingClient = new Client();
//        when(clientRepository.findByName(dto.name())).thenReturn(Optional.of(existingClient));
//
//        assertThrows(RuntimeException.class, () -> clientService.register(dto));
//        verify(clientRepository, never()).save(any(Client.class));
//    }
//}