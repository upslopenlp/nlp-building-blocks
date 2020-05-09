/*******************************************************************************
 * Copyright 2019 Mountain Fog, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.mtnfog.test.idyl.e3.modelgenerator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.mockito.Mockito;

import com.mtnfog.idyl.e3.modelgenerator.BaseStandardModelGenerator;
import com.mtnfog.idyl.e3.modelgenerator.PartsOfSpeechModelGenerator;

import ai.idylnlp.model.manifest.ModelManifestUtils;
import ai.idylnlp.model.manifest.StandardModelManifest;
import ai.idylnlp.models.opennlp.training.model.TrainingAlgorithm;
import ai.idylnlp.training.definition.model.TrainingDefinitionReader;
import ai.idylnlp.training.definition.xml.Trainingdefinition;
import ai.idylnlp.training.definition.xml.Trainingdefinition.Algorithm;
import ai.idylnlp.training.definition.xml.Trainingdefinition.Model;
import ai.idylnlp.training.definition.xml.Trainingdefinition.Trainingdata;

public class PartsOfSpeechModelGeneratorTest {

	private static final String TRAINING_DATA_PATH = new File("src/test/resources/").getAbsolutePath();
	private static final String INPUT_FILE = TRAINING_DATA_PATH + File.separator + "pos-train.txt";
	
	@Test
	public void maxentQn() throws Exception {
		
		File modelFile = TestUtils.getRandomModelFile();
		
		Algorithm algorithm = Mockito.mock(Algorithm.class);
		when(algorithm.getCutoff()).thenReturn(BigInteger.ONE);
		when(algorithm.getIterations()).thenReturn(BigInteger.ONE);
		when(algorithm.getThreads()).thenReturn(BigInteger.ONE);
		when(algorithm.getL1()).thenReturn(BigDecimal.ONE);
		when(algorithm.getL2()).thenReturn(BigDecimal.ONE);
		when(algorithm.getM()).thenReturn(BigInteger.ONE);
		when(algorithm.getMax()).thenReturn(BigInteger.ONE);
		when(algorithm.getName()).thenReturn(TrainingAlgorithm.MAXENT_QN.getName());
		
		Trainingdata trainingdata = Mockito.mock(Trainingdata.class);
		when(trainingdata.getAnnotations()).thenReturn("none");
		when(trainingdata.getFormat()).thenReturn("opennlp");
		when(trainingdata.getFile()).thenReturn(INPUT_FILE);
		
		Trainingdefinition trainingDefinition = Mockito.mock(Trainingdefinition.class);
		when(trainingDefinition.getAlgorithm()).thenReturn(algorithm);
		when(trainingDefinition.getTrainingdata()).thenReturn(trainingdata);
		
		Model model = Mockito.mock(Model.class);
		when(model.getName()).thenReturn("test-model");
		when(model.getFile()).thenReturn(modelFile.getAbsolutePath());
		when(model.getLanguage()).thenReturn("eng");
		when(model.getType()).thenReturn(StandardModelManifest.POS);
		
		TrainingDefinitionReader reader = Mockito.mock(TrainingDefinitionReader.class);
		when(reader.getTrainingDefinition()).thenReturn(trainingDefinition);
		when(reader.getTrainingDefinition().getAlgorithm()).thenReturn(algorithm);
		when(reader.getTrainingDefinition().getModel()).thenReturn(model);
		
		BaseStandardModelGenerator modelGenerator = new PartsOfSpeechModelGenerator();
		File manifestFile = modelGenerator.train(reader);
		
		// Verify the manifest file was generated correctly.
		StandardModelManifest modelManifest = (StandardModelManifest) ModelManifestUtils.readManifest(manifestFile.getAbsolutePath());
		assertEquals("eng", modelManifest.getLanguageCode().getAlpha3().toString());
		assertEquals(StandardModelManifest.POS, modelManifest.getType());
		assertEquals("test-model", modelManifest.getName());
		assertEquals(modelFile.getAbsolutePath(), modelManifest.getModelFileName());
		assertEquals(StandardModelManifest.DEFAULT_SUBTYPE, modelManifest.getSubtype());
		assertEquals(StandardModelManifest.DEFAULT_BEAM_SIZE, modelManifest.getBeamSize());
		assertEquals(modelGenerator.getCreatorVersion(), modelManifest.getCreatorVersion());
		
		manifestFile.delete();
		
	}
	
	@Test
	public void perceptron() throws Exception {
		
		File modelFile = TestUtils.getRandomModelFile();
		
		Algorithm algorithm = Mockito.mock(Algorithm.class);
		when(algorithm.getCutoff()).thenReturn(BigInteger.ONE);
		when(algorithm.getIterations()).thenReturn(BigInteger.ONE);
		when(algorithm.getThreads()).thenReturn(BigInteger.ONE);
		when(algorithm.getName()).thenReturn(TrainingAlgorithm.PERCEPTRON.getName());
		
		Trainingdata trainingdata = Mockito.mock(Trainingdata.class);
		when(trainingdata.getAnnotations()).thenReturn("none");
		when(trainingdata.getFormat()).thenReturn("opennlp");
		when(trainingdata.getFile()).thenReturn(INPUT_FILE);
		
		Trainingdefinition trainingDefinition = Mockito.mock(Trainingdefinition.class);
		when(trainingDefinition.getAlgorithm()).thenReturn(algorithm);
		when(trainingDefinition.getTrainingdata()).thenReturn(trainingdata);
		
		Model model = Mockito.mock(Model.class);
		when(model.getName()).thenReturn("test-model");
		when(model.getFile()).thenReturn(modelFile.getAbsolutePath());
		when(model.getLanguage()).thenReturn("eng");
		when(model.getType()).thenReturn(StandardModelManifest.POS);
		
		TrainingDefinitionReader reader = Mockito.mock(TrainingDefinitionReader.class);
		when(reader.getTrainingDefinition()).thenReturn(trainingDefinition);
		when(reader.getTrainingDefinition().getAlgorithm()).thenReturn(algorithm);
		when(reader.getTrainingDefinition().getModel()).thenReturn(model);
		
		BaseStandardModelGenerator modelGenerator = new PartsOfSpeechModelGenerator();
		File manifestFile = modelGenerator.train(reader);
		
		// Verify the manifest file was generated correctly.
		StandardModelManifest modelManifest = (StandardModelManifest) ModelManifestUtils.readManifest(manifestFile.getAbsolutePath());
		assertEquals("eng", modelManifest.getLanguageCode().getAlpha3().toString());
		assertEquals(StandardModelManifest.POS, modelManifest.getType());
		assertEquals("test-model", modelManifest.getName());
		assertEquals(modelFile.getAbsolutePath(), modelManifest.getModelFileName());
		assertEquals(StandardModelManifest.DEFAULT_SUBTYPE, modelManifest.getSubtype());
		assertEquals(StandardModelManifest.DEFAULT_BEAM_SIZE, modelManifest.getBeamSize());
		assertEquals(modelGenerator.getCreatorVersion(), modelManifest.getCreatorVersion());
		
		manifestFile.delete();
		
	}
	
}
