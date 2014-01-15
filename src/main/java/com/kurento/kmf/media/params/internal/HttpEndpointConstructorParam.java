/*
 * (C) Copyright 2013 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.kurento.kmf.media.params.internal;

import static com.kurento.kms.thrift.api.KmsMediaHttpEndPointTypeConstants.CONSTRUCTOR_PARAMS_DATA_TYPE;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

import com.google.common.base.Objects;
import com.kurento.kmf.common.exception.KurentoMediaFrameworkException;
import com.kurento.kmf.media.internal.ProvidesMediaParam;
import com.kurento.kms.thrift.api.KmsMediaHttpEndPointConstructorParams;

/**
 * @author Ivan Gracia (igracia@gsyc.es)
 * 
 */
@ProvidesMediaParam(type = CONSTRUCTOR_PARAMS_DATA_TYPE)
public class HttpEndpointConstructorParam extends
		AbstractThriftSerializedMediaParam {

	private Integer disconnectionTimeout;

	public Integer getDisconnectionTimeout() {
		return disconnectionTimeout;
	}

	public void setDisconnectionTimeout(Integer disconnectTimeout) {
		this.disconnectionTimeout = disconnectTimeout;
	}

	public HttpEndpointConstructorParam() {
		super(CONSTRUCTOR_PARAMS_DATA_TYPE);
	}

	@Override
	protected TProtocol serializeDataToThrift(TProtocol pr) {
		KmsMediaHttpEndPointConstructorParams kmsParams = new KmsMediaHttpEndPointConstructorParams();

		if (this.disconnectionTimeout != null) {
			kmsParams.setDisconnectionTimeout(disconnectionTimeout.intValue());
		}

		try {
			kmsParams.write(pr);
		} catch (TException e) {
			// TODO change error code
			throw new KurentoMediaFrameworkException(e.getMessage(), e, 30000);
		}
		return pr;
	}

	@Override
	protected void deserializeFromTProtocol(TProtocol pr) {
		KmsMediaHttpEndPointConstructorParams kmsParams = new KmsMediaHttpEndPointConstructorParams();
		try {
			kmsParams.read(pr);
		} catch (TException e) {
			// TODO change error code
			throw new KurentoMediaFrameworkException(e.getMessage(), 30000);
		}

		if (kmsParams.isSetDisconnectionTimeout()) {
			this.disconnectionTimeout = Integer.valueOf(kmsParams
					.getDisconnectionTimeout());
		}

	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}

		HttpEndpointConstructorParam param = (HttpEndpointConstructorParam) obj;
		return Objects.equal(this.disconnectionTimeout,
				param.disconnectionTimeout);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(disconnectionTimeout);
	}

}
