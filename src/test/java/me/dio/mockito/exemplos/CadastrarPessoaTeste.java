package me.dio.mockito.exemplos;

import me.dio.mockito.exemplos.cadastraPessoa.ApiDosCorreios;
import me.dio.mockito.exemplos.cadastraPessoa.CadastrarPessoa;
import me.dio.mockito.exemplos.cadastraPessoa.DadosLocalizacao;
import me.dio.mockito.exemplos.cadastraPessoa.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class CadastrarPessoaTeste {

    @Mock
    private ApiDosCorreios apiDosCorreios;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @Test
    void validarDadosDeCadastro() {
        DadosLocalizacao dadosLocalizacao = new DadosLocalizacao("MG", "Belo Horizonte", "Rua 3", "Apto", "Centro");
        Mockito.when(apiDosCorreios.buscarDadosComBaseNoCep(anyString())).thenReturn(dadosLocalizacao);
        Pessoa pessoa = cadastrarPessoa.cadastrarPessoa("Lucas", "74689765", LocalDate.now(), "7986767");

        DadosLocalizacao enderecoLucas = pessoa.getEndereco();
        assertEquals(dadosLocalizacao.getBairro(), enderecoLucas.getBairro());
        assertEquals(dadosLocalizacao.getCidade(), enderecoLucas.getCidade());
        assertEquals(dadosLocalizacao.getUf(), enderecoLucas.getUf());
    }

    @Test
    void lancarExceptionQuandoChamarApiDosCorreios() {

        doThrow(IllegalArgumentException.class)
                .when(apiDosCorreios)
                .buscarDadosComBaseNoCep(anyString());

        // Mockito.when(apiDosCorreios.buscarDadosComBaseNoCep(anyString())).thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> cadastrarPessoa.cadastrarPessoa("Lucas", "74689765", LocalDate.now(), "7986767"));
    }
}
