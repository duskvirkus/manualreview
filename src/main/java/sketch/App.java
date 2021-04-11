package sketch;

import java.security.Key;
import java.util.ArrayList;



import org.apache.commons.cli.*;

import processing.core.*;

/**
 * Hello world! Processing App with command line arguments.
 *
 */
public class App extends PApplet
{

	static String INPUT_FOLDER = null;
	static String OUTPUT_FOLDER = null;

	PImageQueue q;

  public void settings(){
		size(1000, 720);
	}

	public void setup() {
		q = new PImageQueue(this, INPUT_FOLDER, OUTPUT_FOLDER);
	}
	
	public void draw(){
		background(255);

		q.show();

		fill(0);
		text("(a)ccept   (r)eject   (u)ndo", 100, 55);
	}

	public void keyPressed() {
		if (key == 'a' || key == 'A') {
			q.accept();
		} else if (key == 'r' || key == 'R') {
			q.reject();
		} else if (key == 'u' || key == 'U') {
			q.undo();
		}
	}
	
	public static void main(String[] args){

		Options options = new Options();
		options.addOption(Option.builder("i")
			.desc("Folder of images to load.")
			.hasArg(true)
			.longOpt("input_folder")
			.build());
		options.addOption(Option.builder("o")
			.desc("Folder to save approved images to.")
			.hasArg(true)
			.longOpt("output_folder")
			.build());

		options.addOption("h", "help", false, "Print this message.");

		CommandLineParser argParser = new DefaultParser();

		try {
			CommandLine line = argParser.parse(options, args);

			if (line.hasOption("help")) {
				printHelp(options);
			}

			if(line.hasOption("input_folder")) {
				INPUT_FOLDER = line.getOptionValue("input_folder");
			}
			if(line.hasOption("output_folder")) {
				OUTPUT_FOLDER = line.getOptionValue("output_folder");
			}

			if (INPUT_FOLDER == null) {
				System.out.println("Please specify an input folder!");
				printHelp(options);
			}

			if (OUTPUT_FOLDER == null) {
				System.out.println("Please specify an output folder!");
				printHelp(options);
			}

		} catch(ParseException e) {
			System.out.println("Unexpected exception: " + e.getMessage());
			e.printStackTrace();
		}

		String[] processingArgs = {"App"};
		App app = new App();
		PApplet.runSketch(processingArgs, app);
	}

	public static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("mvn exec:java -Dexec.mainClass=sketch.App  -Dexec.args=\"[options]\"", options);
		System.exit(1);
	}
}
