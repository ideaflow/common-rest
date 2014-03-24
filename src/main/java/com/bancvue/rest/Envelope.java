package com.bancvue.rest;

import lombok.Data;
import lombok.experimental.Builder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Envelope<T> {

	private T data;

	private Envelope() {}

	public Envelope(Envelope<T> env) {
		this.data = env.data;
	}

	public T getData() {
		return data;
	}

	public static class Builder<T> {
		private Envelope<T> env;

		public Builder() {
			this(null);
		}

		public Builder(T data) {
			env = new Envelope<T>();

			data(data);
		}

		public Builder<T> data(T data) {
			env.data = data;
			return this;
		}

		public Envelope<T> build() {
			return new Envelope<T>(env);
		}
	}

}