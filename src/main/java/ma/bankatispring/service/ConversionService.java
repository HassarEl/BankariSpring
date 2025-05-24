package ma.bankatispring.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ConversionService {

    // Taux de conversion par rapport au dirham marocain (MAD)
    private static final Map<String, BigDecimal> RATES = Map.of(
            "MAD", BigDecimal.valueOf(1.0),
            "USD", BigDecimal.valueOf(0.091),  // 1 MAD ≈ 0.091 USD
            "EUR", BigDecimal.valueOf(0.084),  // 1 MAD ≈ 0.084 EUR
            "GBP", BigDecimal.valueOf(0.073)   // 1 MAD ≈ 0.073 GBP
    );

    /**
     * Convertit un montant exprimé en MAD vers la devise cible.
     *
     * @param montantMAD    le montant en MAD (peut provenir d'un Double)
     * @param deviseCible   code de la devise cible ("MAD", "USD", "EUR", "GBP")
     * @return montant converti, arrondi à 2 décimales
     */
    public BigDecimal convert(Double montantMAD, String deviseCible) {
        BigDecimal base = BigDecimal.valueOf(montantMAD);
        BigDecimal taux = RATES.getOrDefault(deviseCible.toUpperCase(), BigDecimal.ONE);
        return base.multiply(taux)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
