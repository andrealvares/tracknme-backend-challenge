package com.desafio.desafiobackend.service.utils;

import com.desafio.desafiobackend.dto.LocationDTO;


public class CalcularDistancia {
    private static final double DISTÂNCIA_LATIITUDE_KM = 111.12;
    private static final double DISTÂNCIA_LONGITUDE_KM = 111.12;
    private static final double DISTANCIA_MINIMA_KM = 0.01; // 0.01 IGUAL A 10 METROS


    private double longitude;
    private double latitude;


    public boolean calcularDistancia(LocationDTO localizacaoA, LocationDTO localizacaoB) throws NullPointerException {
        CalcularDistancia ca = new CalcularDistancia();
        ca.setLatitude(localizacaoA.getLatitude());
        ca.setLongitude(localizacaoA.getLongitude());
        ca.toKm();

        CalcularDistancia cb = new CalcularDistancia();
        cb.setLatitude(localizacaoB.getLatitude());
        cb.setLongitude(localizacaoB.getLongitude());
        cb.toKm();

        // ------ O CALCULO SERÁ FEITO COM BASE NO PLANO CARTESIANO -----

        // LOCALIZAÇÃO A
        Double eixoX_localizacaoA = ca.getLatitude();
        Double eixoY_localizacaoA = ca.getLongitude();

        // LOCALIZAÇÃO B
        Double eixoX_localizacaoB = cb.getLatitude();
        Double eixoY_localizacaoB = cb.getLongitude();

        double distancia = 0;

        //EIXOS Y DIFERENTES E EIXOS X IGUAIS
        if ((eixoY_localizacaoA.doubleValue() != eixoY_localizacaoB.doubleValue()) && (eixoX_localizacaoA.doubleValue() == eixoX_localizacaoB.doubleValue())) {

            distancia = eixoY_localizacaoA > eixoY_localizacaoB ? eixoY_localizacaoA - eixoY_localizacaoB : eixoY_localizacaoB - eixoY_localizacaoA;

            //EIXOS X DIFERENTES E EIXOS Y IGUAIS
        } else if ((eixoX_localizacaoA.doubleValue() != eixoX_localizacaoB.doubleValue()) && (eixoY_localizacaoA.doubleValue() == eixoY_localizacaoB.doubleValue())) {

            distancia = eixoX_localizacaoA > eixoX_localizacaoB ? eixoX_localizacaoA - eixoX_localizacaoB : eixoX_localizacaoB - eixoX_localizacaoA;

        } else {

            // DISTÂNCIA COM EIXOS Y E X DIFERENTES
            double catetoOposto = eixoY_localizacaoA > eixoY_localizacaoB ? eixoY_localizacaoA - eixoY_localizacaoB : eixoY_localizacaoB - eixoY_localizacaoA;
            double catetoAdjacente = eixoX_localizacaoA > eixoX_localizacaoB ? eixoX_localizacaoA - eixoX_localizacaoB : eixoX_localizacaoB - eixoX_localizacaoA;

            distancia = ((catetoOposto * catetoOposto) + (catetoAdjacente * catetoAdjacente));
            distancia = Math.sqrt(distancia);
        }

        return distancia < DISTANCIA_MINIMA_KM;

    }

    // Converte Graus para Quilômetros
    private void toKm() {
        this.latitude *= DISTÂNCIA_LATIITUDE_KM;
        this.longitude *= (DISTÂNCIA_LONGITUDE_KM * (Math.cos(this.latitude)));

    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
