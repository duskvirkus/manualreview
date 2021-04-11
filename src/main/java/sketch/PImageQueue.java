package sketch;

import java.util.ArrayList;
import processing.core.*;
import java.io.File;
import java.util.Arrays;

public class PImageQueue {
  
  ArrayList<PImage> queue = new ArrayList<PImage>();
  ArrayList<String> imagePaths = new ArrayList<String>();
  ArrayList<Boolean> accepted = new ArrayList<Boolean>();

  PApplet app;
  String inputFolder;
  String outputFolder;
  int loadIndex = 0;
  int loadMax = 16;
  int undoBufferSize = 5;
  int imagePathsOffset = 0;

  int currentIndex = 0;

  public PImageQueue(PApplet app, String inputFolder, String outputFolder) {
    this.app = app;
    this.inputFolder = inputFolder;
    this.outputFolder = outputFolder;
    File input = new File(this.inputFolder);
		imagePaths.addAll(Arrays.asList(input.list()));

    if (imagePaths.size() < loadMax) {
      loadMax = imagePaths.size();
    }

    while(queue.size() < loadMax) {
      addNextToQueue();
    }
  }

  public void addNextToQueue() {
    if (loadIndex < imagePaths.size()) {
      PImage temp = app.loadImage(inputFolder + '/' + imagePaths.get(loadIndex));
      queue.add(temp);
      loadIndex++;
    }
  }

  public void accept() {
    accepted.add(true);
    currentIndex++;
    checkCurrentIndex();
  }

  public void reject() {
    accepted.add(false);
    currentIndex++;
    checkCurrentIndex();
  }

  public void undo() {
    accepted.remove(accepted.size() - 1);
    currentIndex--;
    checkCurrentIndex();
  }

  public void checkCurrentIndex() {
    if (currentIndex > undoBufferSize) {
      currentIndex = undoBufferSize;
      moveQueueForward();
    }
    if (currentIndex < 0) {
      currentIndex = 0;
    }
  }

  public void removeFirst() {
    PImage first = queue.get(0);
    if (accepted.get(0).booleanValue()) {
      first.save(outputFolder + '/' + imagePaths.get(imagePathsOffset));
    }
    imagePathsOffset++;

    queue.remove(0);
    accepted.remove(0);
  }

  public void moveQueueForward() {
    removeFirst();
    addNextToQueue();
  }

  public void show() {
    int BORDER = 100;
    int size = app.height - BORDER * 2;

    while (currentIndex >= queue.size()) {
      removeFirst();
      currentIndex -= 2;
    }
    if (queue.size() <= 0) {
      System.exit(0);
    }
		drawImageAspectRatio(queue.get(currentIndex), BORDER, BORDER, size, size);

    float thumbnailSize = size/(float)queue.size();
    for (int i = 0; i < queue.size(); ++i) {
      float y = thumbnailSize * i + BORDER;
      drawImageAspectRatio(queue.get(i), size + BORDER * 2, y, thumbnailSize, thumbnailSize);
      float half = thumbnailSize / 2;

      if (i < accepted.size()) {
        if (accepted.get(i).booleanValue()) {
          app.fill(0, 255, 0);
        } else {
          app.fill(255, 0, 0);
        }
      } else {
        app.noFill();
      }

      if (i == currentIndex) {
        float x1 = size + BORDER * 2 + thumbnailSize * 1.5f;
        float y1 = y + half;
        app.triangle(
          x1, y1,
          x1 + half, y1 - half / 2,
          x1 + half, y1 + half / 2
        );
      } else {
        app.rect(size + BORDER * 2 + thumbnailSize * 1.5f, y + half/2, half, half);
      }
    }
  }

  public void drawImageAspectRatio(PImage img, float x, float y, float w, float h) {
    if (img.width > img.height) {
      float hi = h * img.height / img.width;
      app.image(img, x, y + ((h - hi) / 2), w, hi);
    } else if (img.height > img.width) {
      float wi = w * img.width / img.height;
      app.image(img, x + ((w - wi) / 2), y, wi, h);
    } else {
      app.image(img, x, y, w, h);
    }

    app.stroke(0);
    app.noFill();
    app.rect(x, y, w, h);
  }

}
