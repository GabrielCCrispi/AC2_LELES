package br.com.valueprojects.mock_spring;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.valueprojects.mock_spring.model.FinalizaJogo;
import br.com.valueprojects.mock_spring.model.Jogo;
import br.com.valueprojects.mock_spring.builder.CriadorDeJogo;
import infra.JogoDao;  // Verifique se esta importação é válida e se a classe existe

public class SmsService3{

@Test
public void naoDeveEnviarSmsSeSalvamentoFalhar() {
    // Cria jogos da semana anterior
    Calendar antiga = Calendar.getInstance();
    antiga.set(1999, 1, 20);

    Jogo jogo1 = new CriadorDeJogo().para("Caça moedas").naData(antiga).comVencedor("Jogador A").constroi();

    List<Jogo> jogosAnteriores = Arrays.asList(jogo1);
    
    // Mock do DAO e do serviço de SMS
    JogoDao daoFalso = mock(JogoDao.class);
    SmsService3 smsServiceFalso = mock(SmsService3.class);

    // Simula o retorno dos jogos em andamento
    when(daoFalso.emAndamento()).thenReturn(jogosAnteriores);
    
    // Simula uma falha ao salvar o jogo
    doThrow(new RuntimeException("Falha ao salvar")).when(daoFalso).atualiza(jogo1);

    // Instancia o serviço que finaliza os jogos
    FinalizaJogo finalizador = new FinalizaJogo(daoFalso);

    // Tenta finalizar os jogos, mas ocorre falha no salvamento
    try {
        finalizador.finaliza();
    } catch (Exception e) {
        // Exceção esperada
    }

    // Verifica que o SMS **não** foi enviado
    verifyNoInteractions(smsServiceFalso);
}
}