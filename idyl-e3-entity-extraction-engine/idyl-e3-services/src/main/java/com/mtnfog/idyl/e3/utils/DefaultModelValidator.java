package com.mtnfog.idyl.e3.utils;

import ai.idylnlp.model.ModelValidator;
import ai.idylnlp.model.exceptions.ValidationException;
import ai.idylnlp.model.manifest.ModelManifest;

public class DefaultModelValidator implements ModelValidator {

	@Override
	public boolean validate(ModelManifest manifest) throws ValidationException {
		return true;
	}
	
}
