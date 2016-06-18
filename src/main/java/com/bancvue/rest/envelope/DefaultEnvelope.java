/**
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
			env = new DefaultEnvelope<T>();

			data(data);
		}

		public Builder<T> data(T data) {
			env.data = data;
			return this;
		}

		public DefaultEnvelope<T> build() {
			return new DefaultEnvelope<T>(env);
		}
	}

}