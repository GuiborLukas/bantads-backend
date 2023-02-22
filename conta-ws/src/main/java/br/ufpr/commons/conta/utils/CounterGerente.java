package br.ufpr.commons.conta.utils;

public class CounterGerente implements Comparable<CounterGerente> {

	private Long gerente;
	private int count;

	public CounterGerente() {
		super();
	}

	public CounterGerente(Long gerente, int count) {
		super();
		this.gerente = gerente;
		this.count = count;
	}

	public Long getGerente() {
		return gerente;
	}

	public void setGerente(Long gerente) {
		this.gerente = gerente;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int compareTo(CounterGerente counter) {
		if (this.count < counter.count) {
			return -1;
		}
		if (this.count > counter.count) {
			return 1;
		}
		return 0;
	}

}
