# Manual Review App

A Processing sketch for manually reviewing images for machine learning datasets.

![Shows a screenshot of a processing application running with visual studio code in the background. The app has an image up in it of a person looking at a painting and 15 images to the side, with squares that are red or green, followed by an arrow followed, by empty squares.](./images/screenshot.png)

## Dependencies

Install maven and I recommend running in vscode with the java extensions installed.

## Usage

```
# message displayed by running ./help
usage: mvn exec:java -Dexec.mainClass=sketch.App  -Dexec.args="[options]"
 -h,--help                  Print this message.
 -i,--input_folder <arg>    Folder of images to load.
 -o,--output_folder <arg>   Folder to save approved images to.
```

For easy use consider using `./run.sh` script. In this case put images to review in a directory at path `./in`. Resulting images will show up in `./out`
