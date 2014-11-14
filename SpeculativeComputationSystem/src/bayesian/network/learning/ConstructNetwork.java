package bayesian.network.learning;

import java.io.File;
import java.io.IOException;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class ConstructNetwork {
	
	Rengine engine;
	
	public ConstructNetwork(String[] args){
		this.engine=new Rengine(args,false,null);
	}
	
	public void TerminateEngine(){
		this.engine.end();
	}
	

	public void NewNetwork(String fileinput) throws Exception{
		
		this.engine.eval("data <- read.csv('"+fileinput+"')");
		//this.engine.
		//REXP result=this.engine.eval("net<-chowliu()");
	
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ConstructNetwork test=new ConstructNetwork(null); 
		//test.CSVToArff("C:/Users/Tiago/Documents/workspace/SpeculativeComputationSystem/colorec2.csv", "C:/Users/Tiago/Documents/workspace/SpeculativeComputationSystem/colorec3.arff");
		
	   // Rengine engine=new Rengine(args,false,null);
		test.engine.eval("data <- read.csv('C:/Users/Tiago/Documents/workspace/SpeculativeComputationSystem/colorec2.csv')");
		
		//System.out.println(result);
		test.TerminateEngine();
		
		System.out.println(System.getProperty("java.library.path"));
	}

}
