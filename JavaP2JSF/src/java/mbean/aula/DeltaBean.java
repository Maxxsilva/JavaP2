package mbean.aula;

import javax.faces.bean.ManagedBean;

/**
 * 
 * @author Mauro Silva
 * 
 * Classe para calcular o valor de delta 
 * 
 */ 



@ManagedBean
public class DeltaBean {

	private Double valorA;
	private Double valorB;
        private Double valorC;
	
	private Double resultado;
	
	public String delta(){
                // DELTA = B² -4 x A x C
		resultado = (valorB*valorB)-4*valorA*valorC;
		return null;
	}

        public Double getValorA() {
            return valorA;
        }

        public void setValorA(Double valorA) {
            this.valorA = valorA;
        }

        public Double getValorB() {
            return valorB;
        }

        public void setValorB(Double valorB) {
            this.valorB = valorB;
        }

        public Double getValorC() {
            return valorC;
        }

        public void setValorC(Double valorC) {
            this.valorC = valorC;
        }

        public Double getResultado() {
            return resultado;
        }

        public void setResultado(Double resultado) {
            this.resultado = resultado;
        }
        
        


	
}
