package main.relogio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Relogio2DTest {
    @Test
    void alternaTemaESentido() {
        Relogio2D relogio = new Relogio2D();

        assertEquals(Tema.CLARO, relogio.getTema());
        assertFalse(relogio.isAntiHorario());

        relogio.alternarTema();
        relogio.alternarSentido();

        assertEquals(Tema.ESCURO, relogio.getTema());
        assertTrue(relogio.isAntiHorario());
    }

    @Test
    void ajustaDimensoesSemUltrapassarLimites() {
        Relogio2D relogio = new Relogio2D();
        Ponteiro ponteiroHoras = relogio.getPonteiroHoras();

        for (int i = 0; i < 20; i++) {
            relogio.ajustarPonteiroHoras(true);
        }
        assertEquals(0.9f, ponteiroHoras.getComprimento(), 0.0001f);
        assertEquals(0.1f, ponteiroHoras.getEspessura(), 0.0001f);

        for (int i = 0; i < 30; i++) {
            relogio.ajustarPonteiroHoras(false);
        }
        assertEquals(0.1f, ponteiroHoras.getComprimento(), 0.0001f);
        assertEquals(0.005f, ponteiroHoras.getEspessura(), 0.0001f);
    }
}
