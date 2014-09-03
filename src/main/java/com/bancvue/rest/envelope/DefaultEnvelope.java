package com.bancvue.rest.envelope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class DefaultEnvelope<T> implements Envelope<T> {

	private T data;

	private DefaultEnvelope() {
	}

	public DefaultEnvelope(DefaultEnvelope<T> env) {
		this.data = env.data;
	}

	public T getData() {
		return data;
	}

	public static class Builder<T> {
		private DefaultEnvelope<T> env;

		public Builder() {
			this(null);
		}

		public Builder(T data) {
			env = new DefaultEnvelope<>();

			data(data);
		}

		public Builder<T> data(T data) {
			env.data = data;
			return this;
		}

		public DefaultEnvelope<T> build() {
			return new DefaultEnvelope<>(env);
		}
	}

}