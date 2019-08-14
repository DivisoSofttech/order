 /*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diviso.graeshoppe.order.client.product.model;

import java.util.List;

/**
 * TODO Provide a detailed description here 
 * @author MayaSanjeev
 * mayabytatech, maya.k.k@lxisoft.com
 */
public class ProductBundle {

	private ProductDTO productDto;
	
	private List<ComboLineItemDTO> comboLineItems;
	
    private List<AuxilaryLineItemDTO> auxilaryLineItems;

	public ProductDTO getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDTO productDto) {
		this.productDto = productDto;
	}


	public List<ComboLineItemDTO> getComboLineItems() {
		return comboLineItems;
	}

	public void setComboLineItems(List<ComboLineItemDTO> comboLineItems) {
		this.comboLineItems = comboLineItems;
	}

	public List<AuxilaryLineItemDTO> getAuxilaryLineItems() {
		return auxilaryLineItems;
	}

	public void setAuxilaryLineItems(List<AuxilaryLineItemDTO> auxilaryLineItems) {
		this.auxilaryLineItems = auxilaryLineItems;
	}

	@Override
	public String toString() {
		return "ProductBuddle [productDto=" + productDto + ", comboLineItems=" + comboLineItems + ", auxilaryLineItems="
				+ auxilaryLineItems + "]";
	}
	
    
    
	
}
