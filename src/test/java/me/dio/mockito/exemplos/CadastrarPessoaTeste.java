package me.dio.mockito.exemplos;

import me.dio.mockito.exemplos.cadastraPessoa.ApiDosCorreios;
import me.dio.mockito.exemplos.cadastraPessoa.CadastrarPessoa;
import me.dio.mockito.exemplos.cadastraPessoa.DadosLocalizacao;
import me.dio.mockito.exemplos.cadastraPessoa.Pessoa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CadastrarPessoaTeste {

    @Mock
    private ApiDosCorreios apiDosCorreios;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @Test
    void validarDadosDeCadastro() {
        DadosLocalizacao dadosLocalizacao = new DadosLocalizacao("MG", "Belo Horizonte", "Rua 3", "Apto", "Centro");
        Mockito.when(apiDosCorreios.buscarDadosComBaseNoCep("7986767")).thenReturn(dadosLocalizacao);
        Pessoa pessoa = cadastrarPessoa.cadastrarPessoa("Lucas", "74689765", LocalDate.now(), "7986767");

        assertEquals("Lucas", pessoa.getNome());
        assertEquals("74689765", pessoa.getDocumento());
        assertEquals("MG", pessoa.getEndereco().getUf());
        assertEquals("MG", pessoa.getEndereco().getUf());
        assertEquals("Apto", pessoa.getEndereco().getComplemento());
    }
}
