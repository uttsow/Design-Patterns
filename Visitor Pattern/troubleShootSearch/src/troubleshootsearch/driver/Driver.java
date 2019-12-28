package troubleshootsearch.driver;

import troubleshootsearch.util.FileProcessor;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.util.Results;
import troubleshootsearch.visitable.MyArrayList;
import troubleshootsearch.visitable.MyTree;
import troubleshootsearch.visitor.ArrayPopulatorVisitor;
import troubleshootsearch.visitor.ExactMatch;
import troubleshootsearch.visitor.SemanticMatch;
import troubleshootsearch.visitor.StemMatch;
import troubleshootsearch.visitor.VisitorI;
import troubleshootsearch.visitor.TreePopulatorVisitor;

public class Driver{

    public static void main(String[] args) {

		/*
		 * As the build.xml specifies the arguments as argX, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if (args.length != 5 || args[0].equals("${arg0}") || args[1].equals("${arg1}") || args[2].equals("${arg2}") || args[3].equals("${arg3}") ||
        args[4].equals("${arg4}")){
			System.err.println("Error: Incorrect number of arguments. Program accepts 5 arguments.");
			System.exit(1);
		}



        //Set the debug level
        int level = Integer.parseInt(args[4]);
        MyLogger.setDebugValue(level);


        //Create file processor
        FileProcessor fp;

        //Create all the visitors
        VisitorI arrayListPopulator = new ArrayPopulatorVisitor();
        VisitorI exactMatch = new ExactMatch();
        VisitorI treePopulator = new TreePopulatorVisitor();
        VisitorI stemMatch = new StemMatch();
        VisitorI semanticMatch = new SemanticMatch(new FileProcessor(args[3]));

        //Create the Elements
        MyArrayList list;
        MyTree tree;

		//Start file processor and read file size
        //Step 1. Open technicalInfo file and populate MyArrayList and MyTree element
		fp = new FileProcessor(args[2]);
        list = new MyArrayList(fp);
        tree = new MyTree(fp);

        // Populate array and tree
        list.accept(arrayListPopulator);
        tree.accept(treePopulator);


        // Step 2. Open userInput file and process it line by line
        fp = new FileProcessor(args[0]);

        list.setFileProcessor(fp);
        tree.setFileProcessor(fp);

        //All element accepts the different visitors and goes throuh visit
        list.accept(exactMatch);
        tree.accept(stemMatch);
        list.accept(semanticMatch);

        //Printing result to stdout and file
        Results result = new Results(fp,((ExactMatch)exactMatch).getExactMatchResult(), ((StemMatch)stemMatch).getStemMatchResult(), ((SemanticMatch)semanticMatch).getSemanticMatchResult());

        result.putToArray();
        result.writeToFile(args[1]);



	}
}
