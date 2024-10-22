package br.com.valueprojects.mock_spring;

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

public class SmsService4 {
@Test
public void deveFinalizarApenasJogosDaSemanaAnterior() {
    // Cria jogos de diferentes datas
    Calendar antiga = Calendar.getInstance();
    antiga.set(1999, 1, 20);
    
    Calendar recente = Calendar.getInstance();
    recente.set(2024, 1, 1); // Data atual

    Jogo jogoAntigo = new CriadorDeJogo().para("Caça moedas").naData(antiga).comVencedor("Jogador A").constroi();
    Jogo jogoRecente = new CriadorDeJogo().para("Derruba barreiras").naData(recente).comVencedor("Jogador B").constroi();

    List<Jogo> jogos = Arrays.asList(jogoAntigo, jogoRecente);
    
    // Mock do DAO e do serviço de SMS
    JogoDao daoFalso = mock(JogoDao.class);
    SmsService smsServiceFalso = mock(SmsService.class);

    // Simula o retorno dos jogos em andamento
    when(daoFalso.emAndamento()).thenReturn(jogos);

    // Instancia o serviço que finaliza os jogos
    FinalizaJogo finalizador = new FinalizaJogo(daoFalso);
    finalizador.finaliza();

    // Verifica que apenas o jogo da semana anterior foi finalizado
    verify(daoFalso, times(1)).atualiza(jogoAntigo);
    verify(daoFalso, times(0)).atualiza(jogoRecente);
    
    // Verifica que o SMS foi enviado apenas ao vencedor do jogo antigo
    verify(smsServiceFalso, times(1)).enviar("Jogador A");
    verify(smsServiceFalso, times(0)).enviar("Jogador B");
}
}