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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mtnfog.idyl.e3.modelgenerator.EntityModelGenerator;
import com.mtnfog.idyl.e3.services.DefaultInitializationService;
import ai.idylnlp.model.manifest.ModelManifestUtils;
import ai.idylnlp.model.manifest.StandardModelManifest;
import ai.idylnlp.training.definition.model.TrainingDefinitionReader;
import ai.idylnlp.training.definition.xml.Trainingdefinition;
import ai.idylnlp.training.definition.xml.Trainingdefinition.Algorithm;
import ai.idylnlp.training.definition.xml.Trainingdefinition.Model;
import ai.idylnlp.training.definition.xml.Trainingdefinition.Trainingdata;
import com.mtnfog.test.TestContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestContext.class, loader=AnnotationConfigContextLoader.class)
public class EntityModelGeneratorTest {

	private static final Logger LOGGER = LogManager.getLogger(EntityModelGeneratorTest.class);
	
	private static final String TRAINING_DATA_PATH = new File("src/test/resources/").getAbsolutePath();
	private static final String FEATURE_GENERATOR_XML = TRAINING_DATA_PATH + File.separator + "default-feature-generators.xml";
	private static final String ENGLISH_PERSON_TRAIN = TRAINING_DATA_PATH + File.separator + "person-train.txt";
	private static final String ARABIC_PERSON_TRAIN = TRAINING_DATA_PATH + File.separator + "arabic_person.train";

	@Autowired
	private DefaultInitializationService initializer;
	
	@Before
	public void before() throws InterruptedException, IOException {

		while (!initializer.isLoaded()) {
			Thread.sleep(1000);
		}
				
	}
	
	@After
	public void after() throws IOException {
		
		LOGGER.info("Deleting generated manifests.");
		final String temp = System.getProperty("java.io.tmpdir");
		
		File dir = new File(temp);
		File [] files = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".manifest");
		    }
		});

		for (File xmlfile : files) {
			Files.deleteIfExists(Paths.get(xmlfile.getAbsolutePath()));
		}
		
	}
	
	@Test
	public void maxentEnglishPersons() throws Exception {
		
		final String features = FileUtils.readFileToString(new File(FEATURE_GENERATOR_XML));
		
		File modelFile = TestUtils.getRandomModelFile();
		
		String modelOutputFile = modelFile.getAbsolutePath();
		
		Trainingdefinition trainingDefinition = new Trainingdefinition();
		
		Algorithm algorithm = new Algorithm();
		algorithm.setName("maxent");
		algorithm.setCutoff(BigInteger.ONE);
		algorithm.setIterations(BigInteger.ONE);
		algorithm.setThreads(BigInteger.ONE);
		trainingDefinition.setAlgorithm(new Algorithm());
		
		Model model = new Model();
		model.setLanguage("eng");
		model.setType("person");
		model.setFile(modelOutputFile);
		model.setName("english-person");
		trainingDefinition.setModel(model);
		
		Trainingdata trainingdata = new Trainingdata();
		trainingdata.setFormat("opennlp");
		trainingdata.setFile(ENGLISH_PERSON_TRAIN);
		trainingDefinition.setTrainingdata(trainingdata);		
		
		TrainingDefinitionReader reader = mock(TrainingDefinitionReader.class);
		when(reader.getTrainingDefinition()).thenReturn(trainingDefinition);
		when(reader.getFeatures()).thenReturn(features);
		
		EntityModelGenerator entityModelGenerator = new EntityModelGenerator();
		entityModelGenerator.train(reader);
		
		// Verify the manifest file was generated correctly.
		StandardModelManifest modelManifest = (StandardModelManifest) ModelManifestUtils.readManifest(modelOutputFile + ".manifest");
		assertEquals(model.getLanguage(), modelManifest.getLanguageCode().getAlpha3().toString());
		assertEquals(model.getType(), modelManifest.getType());
		assertEquals(model.getName(), modelManifest.getName());
		assertEquals(model.getFile(), modelManifest.getModelFileName());
		assertEquals(StandardModelManifest.DEFAULT_SUBTYPE, modelManifest.getSubtype());
		assertEquals(StandardModelManifest.DEFAULT_BEAM_SIZE, modelManifest.getBeamSize());
		assertEquals(entityModelGenerator.getCreatorVersion(), modelManifest.getCreatorVersion());
		
		File manifestFile = new File(modelOutputFile + ".manifest");
		manifestFile.delete();
		
	}

	@Test
	public void maxentArabicPersons() throws Exception {
		
		final String features = FileUtils.readFileToString(new File(FEATURE_GENERATOR_XML));
		
		File modelFile = TestUtils.getRandomModelFile();
		
		String modelOutputFile = modelFile.getAbsolutePath();
		
		Trainingdefinition trainingDefinition = new Trainingdefinition();
		
		Algorithm algorithm = new Algorithm();
		algorithm.setName("maxent");
		algorithm.setCutoff(BigInteger.ONE);
		algorithm.setIterations(BigInteger.ONE);
		algorithm.setThreads(BigInteger.ONE);
		trainingDefinition.setAlgorithm(new Algorithm());
		
		Model model = new Model();
		model.setLanguage("eng");
		model.setType("person");
		model.setFile(modelOutputFile);
		model.setName("english-person");
		trainingDefinition.setModel(model);
		
		Trainingdata trainingdata = new Trainingdata();
		trainingdata.setFormat("opennlp");
		trainingdata.setFile(ARABIC_PERSON_TRAIN);
		trainingDefinition.setTrainingdata(trainingdata);		
		
		TrainingDefinitionReader reader = mock(TrainingDefinitionReader.class);
		when(reader.getTrainingDefinition()).thenReturn(trainingDefinition);
		when(reader.getFeatures()).thenReturn(features);
		
		EntityModelGenerator entityModelGenerator = new EntityModelGenerator();
		entityModelGenerator.train(reader);
		
		// Verify the manifest file was generated correctly.
		StandardModelManifest modelManifest = (StandardModelManifest) ModelManifestUtils.readManifest(modelOutputFile + ".manifest");
		assertEquals(model.getLanguage(), modelManifest.getLanguageCode().getAlpha3().toString());
		assertEquals(model.getType(), modelManifest.getType());
		assertEquals(model.getName(), modelManifest.getName());
		assertEquals(model.getFile(), modelManifest.getModelFileName());
		assertEquals(StandardModelManifest.DEFAULT_SUBTYPE, modelManifest.getSubtype());
		assertEquals(StandardModelManifest.DEFAULT_BEAM_SIZE, modelManifest.getBeamSize());
		assertEquals(entityModelGenerator.getCreatorVersion(), modelManifest.getCreatorVersion());
		
		File manifestFile = new File(modelOutputFile + ".manifest");
		manifestFile.delete();
		
	}
	
	@Test
	public void maxentQn() throws Exception {
		
		final String features = FileUtils.readFileToString(new File(FEATURE_GENERATOR_XML));
		
		File modelFile = TestUtils.getRandomModelFile();
		
		String modelOutputFile = modelFile.getAbsolutePath();
		
		Trainingdefinition trainingDefinition = new Trainingdefinition();
		
		Algorithm algorithm = new Algorithm();
		algorithm.setName("maxent-qn");
		algorithm.setCutoff(BigInteger.ONE);
		algorithm.setIterations(BigInteger.ONE);
		algorithm.setThreads(BigInteger.ONE);
		algorithm.setL1(BigDecimal.valueOf(0.1));
		algorithm.setL2(BigDecimal.valueOf(0.1));
		algorithm.setM(BigInteger.ONE);
		algorithm.setMax(BigInteger.valueOf(30000));
		trainingDefinition.setAlgorithm(new Algorithm());
		
		Model model = new Model();
		model.setLanguage("eng");
		model.setType("person");
		model.setFile(modelOutputFile);
		model.setName("english-person");
		trainingDefinition.setModel(model);
		
		Trainingdata trainingdata = new Trainingdata();
		trainingdata.setFormat("opennlp");
		trainingdata.setFile(ARABIC_PERSON_TRAIN);
		trainingDefinition.setTrainingdata(trainingdata);		
		
		TrainingDefinitionReader reader = mock(TrainingDefinitionReader.class);
		when(reader.getTrainingDefinition()).thenReturn(trainingDefinition);
		when(reader.getFeatures()).thenReturn(features);
		
		EntityModelGenerator entityModelGenerator = new EntityModelGenerator();
		entityModelGenerator.train(reader);
		
		// Verify the manifest file was generated correctly.
		StandardModelManifest modelManifest = (StandardModelManifest) ModelManifestUtils.readManifest(modelOutputFile + ".manifest");
		assertEquals(model.getLanguage(), modelManifest.getLanguageCode().getAlpha3().toString());
		assertEquals(model.getType(), modelManifest.getType());
		assertEquals(model.getName(), modelManifest.getName());
		assertEquals(model.getFile(), modelManifest.getModelFileName());
		assertEquals(StandardModelManifest.DEFAULT_SUBTYPE, modelManifest.getSubtype());
		assertEquals(StandardModelManifest.DEFAULT_BEAM_SIZE, modelManifest.getBeamSize());
		assertEquals(entityModelGenerator.getCreatorVersion(), modelManifest.getCreatorVersion());
		
		File manifestFile = new File(modelOutputFile + ".manifest");
		manifestFile.delete();
		
	}
	
	@Test
	public void perceptrontEnglishPersons() throws Exception {
		
		final String features = FileUtils.readFileToString(new File(FEATURE_GENERATOR_XML));
		
		File modelFile = TestUtils.getRandomModelFile();
		
		String modelOutputFile = modelFile.getAbsolutePath();
		
		Trainingdefinition trainingDefinition = new Trainingdefinition();
		
		Algorithm algorithm = new Algorithm();
		algorithm.setName("perceptron");
		algorithm.setCutoff(BigInteger.ONE);
		algorithm.setIterations(BigInteger.ONE);
		algorithm.setThreads(BigInteger.ONE);
		trainingDefinition.setAlgorithm(new Algorithm());
		
		Model model = new Model();
		model.setLanguage("eng");
		model.setType("person");
		model.setFile(modelOutputFile);
		model.setName("english-person");
		trainingDefinition.setModel(model);
		
		Trainingdata trainingdata = new Trainingdata();
		trainingdata.setFormat("opennlp");
		trainingdata.setFile(ENGLISH_PERSON_TRAIN);
		trainingDefinition.setTrainingdata(trainingdata);		
		
		TrainingDefinitionReader reader = mock(TrainingDefinitionReader.class);
		when(reader.getTrainingDefinition()).thenReturn(trainingDefinition);
		when(reader.getFeatures()).thenReturn(features);
		
		EntityModelGenerator entityModelGenerator = new EntityModelGenerator();
		entityModelGenerator.train(reader);
		
		// Verify the manifest file was generated correctly.
		StandardModelManifest modelManifest = (StandardModelManifest) ModelManifestUtils.readManifest(modelOutputFile + ".manifest");
		assertEquals(model.getLanguage(), modelManifest.getLanguageCode().getAlpha3().toString());
		assertEquals(model.getType(), modelManifest.getType());
		assertEquals(model.getName(), modelManifest.getName());
		assertEquals(model.getFile(), modelManifest.getModelFileName());
		assertEquals(StandardModelManifest.DEFAULT_SUBTYPE, modelManifest.getSubtype());
		assertEquals(StandardModelManifest.DEFAULT_BEAM_SIZE, modelManifest.getBeamSize());
		assertEquals(entityModelGenerator.getCreatorVersion(), modelManifest.getCreatorVersion());
		
		File manifestFile = new File(modelOutputFile + ".manifest");
		manifestFile.delete();
		
	}
	
}
