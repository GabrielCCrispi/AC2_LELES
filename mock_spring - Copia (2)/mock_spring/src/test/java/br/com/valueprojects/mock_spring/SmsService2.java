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


public class  SmsService2{
@Test
public void deveSalvarJogosEEnviarSmsAoVencedor() {
    // Cria jogos da semana anterior
    Calendar antiga = Calendar.getInstance();
    antiga.set(1999, 1, 20);

    Jogo jogo1 = new CriadorDeJogo().para("Caça moedas").naData(antiga).comVencedor("Jogador A").constroi();
    Jogo jogo2 = new CriadorDeJogo().para("Derruba barreiras").naData(antiga).comVencedor("Jogador B").constroi();
    
    List<Jogo> jogosAnteriores = Arrays.asList(jogo1, jogo2);
    
    // Mock do DAO e do serviço de SMS
    JogoDao daoFalso = mock(JogoDao.class);
    SmsService smsServiceFalso = mock(SmsService.class);
    
    // Simula o retorno dos jogos em andamento
    when(daoFalso.emAndamento()).thenReturn(jogosAnteriores);
    
    // Instancia o serviço que finaliza os jogos
    FinalizaJogo finalizador = new FinalizaJogo(daoFalso);
    finalizador.finaliza();

    // Verifica que os jogos foram salvos
    verify(daoFalso, times(1)).atualiza(jogo1);
    verify(daoFalso, times(1)).atualiza(jogo2);
    
    // Verifica que o SMS foi enviado para os vencedores
    verify(smsServiceFalso, times(1)).enviar("Jogador A");
    verify(smsServiceFalso, times(1)).enviar("Jogador B");
}
}