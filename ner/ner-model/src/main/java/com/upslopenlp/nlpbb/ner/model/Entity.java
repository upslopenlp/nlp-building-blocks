package com.upslopenlp.nlpbb.ner.model;

public class Entity {

  private String text;
  private long confidence;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public long getConfidence() {
    return confidence;
  }

  public void setConfidence(long confidence) {
    this.confidence = confidence;
  }

}
