package cguide.parser;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import cguide.execution.entities.Action;
import cguide.execution.entities.ClinicalAction;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.Condition;
import cguide.execution.entities.ConditionSet;
import cguide.execution.entities.Decision;
import cguide.execution.entities.Duration;
import cguide.execution.entities.End;
import cguide.execution.entities.Exam;
import cguide.execution.entities.Formula;
import cguide.execution.entities.Guideline;
import cguide.execution.entities.MedicationRecommendation;
import cguide.execution.entities.NonMedicationRecommendation;
import cguide.execution.entities.Option;
import cguide.execution.entities.Outcome;
import cguide.execution.entities.Parameter;
import cguide.execution.entities.Periodicity;
import cguide.execution.entities.Plan;
import cguide.execution.entities.PreCondition;
import cguide.execution.entities.Procedure;
import cguide.execution.entities.Question;
import cguide.execution.entities.Scope;
import cguide.execution.entities.TemporalRestriction;
import cguide.execution.entities.TriggerCondition;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class GuidelineHandler {
    private String baseURL;
    private String filename;
    private OWLObjectRenderer renderer;
    private OWLOntologyManager manager;
    private OWLOntology ontology;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private OWLDataFactory factory;
    private PrefixOWLOntologyFormat pm;

    public GuidelineHandler(){

        this.baseURL = "http://www.semanticweb.org/ontologies/2012/3/CompGuide.owl";
        this.filename = "file:///c://Cguide.owl";
        renderer = null;
        manager = null;
        ontology = null;
        reasonerFactory = null;
        reasoner = null;
        factory = null;
        pm = null;

    }
    public GuidelineHandler(String baseURL, String filename){
        this.baseURL = baseURL;
        this.filename = filename;
        renderer = null;
        manager = null;
        ontology = null;
        reasonerFactory = null;
        reasoner = null;
        factory = null;
        pm = null;

    }


    public Boolean loadGuideline()  {

        //prepare ontology and reasoner
        this.manager= OWLManager.createOWLOntologyManager();
        //OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(BASE_URL));
        try {
            this.ontology= manager.loadOntologyFromOntologyDocument(IRI.create(filename));
        } catch (OWLOntologyCreationException e) {
            return false;
        }
        this.factory = manager.getOWLDataFactory();
        this.pm= (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
        this.pm.setDefaultPrefix(baseURL+ "#");
        System.out.println(this.pm);
        return true;

    }

    public ArrayList<String > getGuidelines(){
        ArrayList<String> guidelines=new ArrayList<String>();
       OWLClass owlClass = this.factory.getOWLClass(IRI.create(baseURL+"#ClinicalPracticeGuideline"));
       Set<OWLIndividual> individuals = owlClass.getIndividuals(ontology);
        for(OWLIndividual individual: individuals){
            guidelines.add(parseIRI(individual.toString()));
        }
       return guidelines;
    }

    public void insertIndividual(){


    }



    public Guideline getClinicalPracticeGuideline(String name){
        Guideline guideline = new Guideline();
        guideline.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        System.out.println("\n\n\n......LOADING Guideline "+name);
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("dateOfLastUpdate")){guideline.setDateofupdate(parseDateTime(literal.getLiteral()));}
                if(field.equals("dateOfCreation")){guideline.setDateofcreation(parseDateTime(literal.getLiteral()));}
                if(field.equals("versionNumber")){guideline.setVersionnumber(literal.getLiteral());}
                if(field.equals("guidelineName")){guideline.setGuidelinename(literal.getLiteral());}
                if(field.equals("authorship")){guideline.setAuthorship(literal.getLiteral());}
                if(field.equals("guidelineDescription")){guideline.setGuidelinedescription(literal.getLiteral());}


            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasPlan")){
                  guideline.setPlan(this.getPlan(parseIRI(literal.toString())));
                    
                }
                if(
                  field.equals("hasScope")){guideline.setScope(this.getScope(parseIRI(literal.toString())));
                }
            }
        }

        if(guideline.getGuidelinename()!=null
                &&guideline.getAuthorship()!=null
                &&guideline.getDateofcreation()!=null
                &&guideline.getDateofupdate()!=null
                &&guideline.getGuidelinedescription()!=null
                &&guideline.getScope()!=null
                &&guideline.getVersionnumber()!=null
                &&guideline.getPlan()!=null)
                {
            return guideline;
        }
        else{
            return null;
        }
    }

    public Scope getScope(String name){
        Scope scope =new Scope();
        scope.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("targetPopulation")){scope.addTargetPopulation(literal.getLiteral());}
                if(field.equals("intendedUser")){scope.addIntendedUser(literal.getLiteral());}
                if(field.equals("diseaseOrCondition")){scope.addDiseageOrCondition(literal.getLiteral());}
            }
        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
           String field = parseIRI(entry.getKey().toString());
           Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                         if(field.equals("hasGuidelineCategory")){scope.addGuidelineCategory(parseIRI(literal.toString()));}
                         if(field.equals("hasClinicalSpecialty")){scope.addClinicalSpecialty(parseIRI(literal.toString()));}
                         if(field.equals("hasScopeConditionSet")){scope.addConditionSet(this.getConditionSet(parseIRI(literal.toString())));}

            }
        }
        if(scope.getConditionSet().size()!=0
                &&scope.getDiseaseOrCondition().size()!=0
                &&scope.getHasClinicalSpeciality().size()!=0
                &&scope.getIntendedUser().size()!=0
                &&scope.getTargetPopulation().size()!=0
                &&scope.getHasGuidelineCategory().size()!=0){
            return scope;

        }else{
            return null;
        }
    }

    public ConditionSet getConditionSet(String name){
        ConditionSet conditionSet =new ConditionSet();
        conditionSet.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("conditionSetCounter")){conditionSet.addConditionSetCounter(Integer.valueOf(literal.getLiteral()));}
            }
        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasComparisonOperator")){conditionSet.addComparisonOperator(parseIRI(literal.toString()));}
                if(field.equals("hasCondition")){conditionSet.addCondition(this.getCondition(parseIRI(literal.toString())));}

            }
        }

        if(conditionSet.getCondition()!=null){
             return conditionSet;
        }else{
             return null;
        }
    }

    public Condition getCondition(String name){
        Condition condition =new Condition();
        condition.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("conditionParameter")){condition.setConditionParameter(literal.getLiteral());}
                if(field.equals("qualitativeValue")){condition.setValue(literal.getLiteral());condition.setIsNumeric(Boolean.FALSE);}
                if(field.equals("numericalValue")){condition.setValue(literal.getLiteral());condition.setIsNumeric(Boolean.TRUE);}
                if(field.equals("unit")){condition.setUnit(literal.getLiteral());}
                if(field.equals("parameterIdentifier")){condition.addParameterIdentifier(literal.getLiteral());}
            }
        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasComparisonOperator")){condition.setComparisonOperator(parseIRI(literal.toString()));}
                if(field.equals("hasTemporalRestriction")){condition.addTemporalRestriction(this.getTemporalRestriction(parseIRI(literal.toString())));}

            }
        }
        if(condition.getValue()!=null
                &&condition.getComparisonOperator()!=null
                &&condition.getUnit()!=null
                &&condition.getConditionParameter()!=null)
        {
            return condition;
        }else{
            return null;
        }
    }

    public TemporalRestriction getTemporalRestriction(String name){
        TemporalRestriction temporalRestriction =new TemporalRestriction();
        temporalRestriction.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("maxTemporalRestrictionValue")){temporalRestriction.setMaxRestrictionValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("minTemporalRestrictionValue")){temporalRestriction.setMinRestrictionValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("temporalRestrictionValue")){temporalRestriction.setMaxRestrictionValue(Double.valueOf(literal.getLiteral()));temporalRestriction.setMinRestrictionValue(Double.valueOf(literal.getLiteral()));}
            }
        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasTemporalOperator")){temporalRestriction.setTemporalOperator(parseIRI(literal.toString()));}
                if(field.equals("hasTemporalUnit")){temporalRestriction.setTemporalUnit(parseIRI(literal.toString()));}
        }
        }
        if(temporalRestriction.getMaxRestrictionValue()!=null
                && temporalRestriction.getMinRestrictionValue()!=null
                && temporalRestriction.getTemporalUnit()!=null
                && temporalRestriction.getTemporalOperator()!=null)
        {
             return temporalRestriction;
        }else{
             return null;
        }
    }

    public ClinicalAction getClinicalAction(String name){
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        //System.out.println(individual.toString());
        
        String type = parseIRI(individual.getTypes(ontology).toString());
        //System.out.println("Type "+type);
        System.out.println("Name "+ name);
        
        if(type.equals("Exam")){
            ClinicalAction exam = this.getExam(name);
            exam.setActionType(type);
            return exam;
        }
        if(type.equals("Formula")){
            ClinicalAction formula = this.getFormula(name);
            formula.setActionType(type);
            return formula;
        }
        if(type.equals("MedicationRecommendation")){
            ClinicalAction medicationRecommendation = this.getMedicationRecommendation(name);
            medicationRecommendation.setActionType(type);
            return medicationRecommendation;
        }
        if(type.equals("NonMedicationRecommendation")){
            ClinicalAction nonMedicationRecommendation = this.getNonMedicationRecommendation(name);
            nonMedicationRecommendation.setActionType(type);
            return nonMedicationRecommendation;
        }
        if(type.equals("Procedure")){
            ClinicalAction procedure = this.getProcedure(name);
            procedure.setActionType(type);
            return procedure;
        }
        return null;
    }

    private Exam getExam(String name) {
        Exam exam =new Exam();
        exam.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("examDescription")){exam.setDescription(literal.getLiteral());}
                if(field.equals("examName")){exam.setName(literal.getLiteral());}
                }
        }
        if(exam.getDescription()!=null
                &&exam.getName()!=null)
        {
            return exam;
        }else{
              return null;
        }
    }

    private Formula getFormula(String name) {
        Formula formula =new Formula();
        formula.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
          for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("mathematicalExpression")){formula.setMathematicalExpression(literal.getLiteral());}
                }
        }

         for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasVariableParameter")){formula.addParameter(this.getParameter(parseIRI(literal.toString())));}
                if(field.equals("hasResultParameter")){formula.setResult(this.getParameter(parseIRI(literal.toString())));}
            }
        }
        if((formula.getParameters().size()!=0)
                &&formula.getResult()!=null
                    &&formula.getMathematicalExpression()!=null)
        {
               return formula;
        }else{
            return null;
        }
    }

    private NonMedicationRecommendation getNonMedicationRecommendation(String name) {
        NonMedicationRecommendation nonMedicationRecommendation =new NonMedicationRecommendation();
        nonMedicationRecommendation.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("nonMedicationRecommendationDescription")){nonMedicationRecommendation.setDescription(literal.getLiteral());}
                if(field.equals("nonMedicationRecommendationName")){nonMedicationRecommendation.setName(literal.getLiteral());}
            }
        }
        if(nonMedicationRecommendation.getDescription()!=null
                &&nonMedicationRecommendation.getName()!=null)
        {
              return nonMedicationRecommendation;
        }else{
               return null;
        }
    }

    private MedicationRecommendation getMedicationRecommendation(String name) {
        MedicationRecommendation medicationRecommendation =  new MedicationRecommendation();
        medicationRecommendation.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
         for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("medicationRecommendationDescription")){medicationRecommendation.setDescription(literal.getLiteral());}
                if(field.equals("activeIngredient")){medicationRecommendation.setActiveIngredient(literal.getLiteral());}
                if(field.equals("medicationRecommendationName")){medicationRecommendation.setName(literal.getLiteral());}
                if(field.equals("dosage")){medicationRecommendation.setDosage(literal.getLiteral());}
                if(field.equals("pharmaceuticalForm")){medicationRecommendation.setPharmaceuticalForm(literal.getLiteral());}
                if(field.equals("posology")){medicationRecommendation.setPosology(literal.getLiteral());}
            }
        }
        if((medicationRecommendation.getName()!=null)
                &&medicationRecommendation.getActiveIngredient()!=null)
        {
            return medicationRecommendation;
        }else{
            return null;
        }
    }

    private Procedure getProcedure(String name) {
        Procedure procedure =new Procedure();
        procedure.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
       for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("procedureDescription")){procedure.setDescription(literal.getLiteral());}
                if(field.equals("procedureName")){procedure.setName(literal.getLiteral());}
            }
        }
        if(procedure.getDescription()!=null
                &&procedure.getName()!=null)
        {
            return procedure;
        }else{
            return null;
        }
    }
    public ClinicalTask getClinicalTask(String name){

        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        String type = parseIRI(individual.getTypes(ontology).toString());
        if(type.equals("Question")){
            Question clinicalTask = this.getQuestion(name);
            if(clinicalTask!=null){
            clinicalTask.setTaskType(type);
                return clinicalTask;
            } else{
            return null;
            }
        }
        if(type.equals("Plan")){
            Plan clinicalTask = this.getPlan(name);
            if(clinicalTask!=null){
                clinicalTask.setTaskType(type);
                return clinicalTask;
            } else{
                return null;
            }
        }
        if(type.equals("Decision")){
            ClinicalTask clinicalTask = this.getDecision(name);
            if(clinicalTask!=null){
                clinicalTask.setTaskType(type);
                return clinicalTask;
            } else{
                return null;
            }
        }
        if(type.equals("Action")){
            Action clinicalTask = this.getAction(name);
            if(clinicalTask!=null){
                clinicalTask.setTaskType(type);
                return clinicalTask;
            } else{
                return null;
            }
        }
        if(type.equals("End")){
            End clinicalTask = this.getEnd(name);
            if(clinicalTask!=null){
                clinicalTask.setTaskType(type);
                return clinicalTask;
            } else{
                return null;
            }
        }


        return null;
    }

    private End getEnd(String name) {
        End end =new End();
        end.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasTriggerCondition")){end.addTriggerCondition(this.getTriggerCondition(parseIRI(literal.toString())));}
            }
        }
        return end;
    }

    private Plan getPlan(String name){
        Plan plan =new Plan();
        plan.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("generalDescription")){plan.setGeneralDescription(literal.getLiteral());}
            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasPreCondition")){plan.addPreCondition(this.getPreCondition(parseIRI(literal.toString())));}
                if(field.equals("hasTriggerCondition")){plan.addTriggerCondition(this.getTriggerCondition(parseIRI(literal.toString())));}
                if(field.equals("hasOutcome")){plan.addOutcome(this.getOutcome(parseIRI(literal.toString())));}
                if(field.equals("alternativeTask")){plan.addClinicalTask(parseIRI(literal.toString()));plan.setTaskFormat(field);}
                if(field.equals("nextTask")){plan.addClinicalTask(parseIRI(literal.toString()));plan.setTaskFormat(field);}
                if(field.equals("parallelTask")){plan.addClinicalTask(parseIRI(literal.toString()));plan.setTaskFormat(field);}
                if(field.equals("preferenceAlternativeTask")){plan.addClinicalTask(parseIRI(literal.toString()));plan.setTaskFormat(field);}
                if(field.equals("hasFirstTask")){plan.addFirstTask(parseIRI(literal.toString()));plan.setFirstTaskFormat(field);}
                if(field.equals("hasDuration")){plan.setDuration(this.getDuration(parseIRI(literal.toString())));}
                if(field.equals("hasPeriodicity")){plan.setPeriodicity(this.getPeriodicity(parseIRI(literal.toString())));}
                if(field.equals("syncTask")){plan.setSyncTask(parseIRI(literal.toString()));}
                if(field.equals("stopConditionTask")){plan.addClinicalTask(parseIRI(literal.toString()));}
            }
        }
        if(plan.getFirstClinicalTask().size()>=0
                &&plan.getFirstClinicalTask()!=null
                &&plan.getGeneralDescription()!=null)
        {
            return plan;
        }else{
            return null;
        }
    }

    private Question getQuestion(String name){
        Question question =new Question();
        question.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));

        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("generalDescription")){question.setGeneralDescription(literal.getLiteral());}
            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasPreCondition")){question.addPreCondition(this.getPreCondition(parseIRI(literal.toString())));}
                if(field.equals("hasTriggerCondition")){question.addTriggerCondition(this.getTriggerCondition(parseIRI(literal.toString())));}
                if(field.equals("hasParameter")){question.addParameter(this.getParameter(parseIRI(literal.toString())));}
                if(field.equals("alternativeTask")){question.addClinicalTask(parseIRI(literal.toString()));question.setTaskFormat(field);}
                if(field.equals("nextTask")){question.addClinicalTask(parseIRI(literal.toString()));question.setTaskFormat(field);}
                if(field.equals("parallelTask")){question.addClinicalTask(parseIRI(literal.toString()));question.setTaskFormat(field);}
                if(field.equals("preferenceAlternativeTask")){question.addClinicalTask(parseIRI(literal.toString()));question.setTaskFormat(field);}
                if(field.equals("syncTask")){question.setSyncTask(parseIRI(literal.toString()));}

            }
        }
        if(question.getClinicalTasks().size()>=0
                &&question.getParameters().size()!=0)
        {
            return question;
        }else{
            return null;
        }
    }



    private Decision getDecision(String name){
        Decision decision =new Decision();
        decision.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("generalDescription")){decision.setGeneralDescription(literal.getLiteral());}
            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasPreCondition")){decision.addPreCondition(this.getPreCondition(parseIRI(literal.toString())));}
                if(field.equals("hasTriggerCondition")){decision.addTriggerCondition(this.getTriggerCondition(parseIRI(literal.toString())));}
                if(field.equals("hasOption")){decision.addOption(this.getOption(parseIRI(literal.toString())));}
                if(field.equals("alternativeTask")){decision.addClinicalTask(parseIRI(literal.toString()));decision.setTaskFormat(field);}
                if(field.equals("nextTask")){decision.addClinicalTask(parseIRI(literal.toString()));decision.setTaskFormat(field);}
                if(field.equals("parallelTask")){decision.addClinicalTask(parseIRI(literal.toString()));decision.setTaskFormat(field);}
                if(field.equals("preferenceAlternativeTask")){decision.addClinicalTask(parseIRI(literal.toString()));decision.setTaskFormat(field);}
                if(field.equals("syncTask")){decision.setSyncTask(parseIRI(literal.toString()));}

            }
        }
        if(decision.getClinicalTasks().size()>=0
                &&decision.getOptions().size()!=0
                &&decision.getGeneralDescription()!=null)
        {
                return decision;
        }else{
            return null;
        }

    }

    private Action getAction(String name){
        Action action =new Action();
        action.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("generalDescription")){action.setGeneralDescription(literal.getLiteral());}
            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasPreCondition")){action.addPreCondition(this.getPreCondition(parseIRI(literal.toString())));}
                if(field.equals("hasTriggerCondition")){action.addTriggerCondition(this.getTriggerCondition(parseIRI(literal.toString())));}
                if(field.equals("hasClinicalActionType")){action.addClinicalAction(this.getClinicalAction(parseIRI(literal.toString())));}
                if(field.equals("alternativeTask")){action.addClinicalTask(parseIRI(literal.toString()));action.setTaskFormat(field);}
                if(field.equals("nextTask")){action.addClinicalTask(parseIRI(literal.toString()));action.setTaskFormat(field);}
                if(field.equals("parallelTask")){action.addClinicalTask(parseIRI(literal.toString()));action.setTaskFormat(field);}
                if(field.equals("preferenceAlternativeTask")){action.addClinicalTask(parseIRI(literal.toString()));action.setTaskFormat(field);}
                if(field.equals("hasOutcome")){action.setOutcome(this.getOutcome(parseIRI(literal.toString())));}
                if(field.equals("syncTask")){action.setSyncTask(parseIRI(literal.toString()));}
                if(field.equals("hasPeriodicity")){action.setPeriodicity(this.getPeriodicity(parseIRI(literal.toString())));}
                if(field.equals("stopConditionTask")){action.addClinicalTask(parseIRI(literal.toString()));}

            }
        }
        if(action.getClinicalTasks().size()>=0
                &&action.getGeneralDescription()!=null)
        {
            return action;
        }else{
            return null;
        }
    }


    public PreCondition getPreCondition(String name){
        PreCondition preCondition = new PreCondition();
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasPreConditionSet")){preCondition.addPrecondition(this.getConditionSet(parseIRI(literal.toString())));}
                }
        }
        if(preCondition.getPrecondition().size()!=0)
        {
            return preCondition;
        }else{
            return null;
        }
    }

    public Outcome getOutcome(String name){
        Outcome outcome = new Outcome();
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasOutcomeConditionSet")){outcome.addOutcome(this.getConditionSet(parseIRI(literal.toString())));}
            }
        }
        if(outcome.getOutcomeConditionSet().size()!=0)
        {
            return outcome;
        }else{
            return null;
        }
    }

    public TriggerCondition getTriggerCondition(String name){
        TriggerCondition triggerCondition = new TriggerCondition();
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasTriggerConditionSet")){triggerCondition.addTriggerCondition(this.getConditionSet(parseIRI(literal.toString())));}
            }
        }
        if(triggerCondition.getTriggerConditionSet().size()!=0)
        {
            return triggerCondition;
        }else{
            return null;
        }
    }

    public Duration getDuration(String name){
        Duration duration =new Duration();
        duration.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("maxDurationValue")){duration.setMaxDurationValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("minDurationValue")){duration.setMinDurationValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("durationValue")){duration.setMinDurationValue(Double.valueOf(literal.getLiteral()));duration.setMaxDurationValue(Double.valueOf(literal.getLiteral()));}
                 }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasTemporalUnit")){duration.setTemporalUnit(parseIRI(literal.toString()));}
            }
        }
        if(duration.getMaxDurationValue()!=null
                &&duration.getTemporalUnit()!=null
                &&duration.getMinDurationValue()!=null)
        {
            return duration;
        }else{
            return null;
        }
    }
    public Periodicity getPeriodicity(String name){
        Periodicity periodicity =new Periodicity();
        periodicity.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("periodicityValue")){periodicity.setMinPeriodicityValue(Double.valueOf(literal.getLiteral()));periodicity.setMaxPeriodicityValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("minPeriodicityValue")){periodicity.setMinPeriodicityValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("maxPeriodicityValue")){periodicity.setMaxPeriodicityValue(Double.valueOf(literal.getLiteral()));}
                if(field.equals("repetitionValue")){periodicity.setRepetitionValue(Integer.valueOf(literal.getLiteral()));}
            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasTemporalUnit")){periodicity.setTemporalUnit(parseIRI(literal.toString()));}
                if(field.equals("hasStopConditionSet")){periodicity.addStopConditionSet(this.getConditionSet(parseIRI(literal.toString())));}

            }
        }
        return periodicity;
    }
    public Option getOption(String name){
        Option option =new Option();
        option.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectPropertyValues = individual.getObjectPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("numericalValue")){option.setNumeric(Boolean.TRUE);option.setValue(literal.getLiteral());}
                if(field.equals("qualitativeValue")){option.setNumeric(Boolean.FALSE);option.setValue(literal.getLiteral());}
                if(field.equals("unit")){option.setUnit(literal.getLiteral());}
                if(field.equals("optionParameter")){option.setOptionParameter(literal.getLiteral());}
            }

        }
        for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry :objectPropertyValues.entrySet()){
            String field = parseIRI(entry.getKey().toString());
            Set<OWLIndividual> s= entry.getValue();
            for(OWLIndividual literal : s){
                if(field.equals("hasOptionConditionSet")){option.addOptionConditionSet(this.getConditionSet(parseIRI(literal.toString())));}

            }
        }
        if(option.getValue()!=null
                &&option.getOptionParameter()!=null
                &&option.getUnit()!=null
                &&option.getOptionConditionSet()!=null)
        {
            return option;
        }else{
            return null;
        }
    }
    public Parameter getParameter(String name){
        Parameter parameter =new Parameter();
        parameter.setId(name);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+name,pm);
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = individual.getDataPropertyValues(manager.getOntology(IRI.create(this.getBaseURL())));
        for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
            String field = parseIRI(entry.getKey().toString());
            Set<OWLLiteral> s =  entry.getValue();
            for(OWLLiteral literal : s){
                if(field.equals("possibleValue")){parameter.addPossibleValue(literal.getLiteral());}
                if(field.equals("variableName")){parameter.addVariableName(literal.getLiteral());}
                if(field.equals("numeric")){parameter.setNumeric(Boolean.valueOf(literal.getLiteral()));}
                if(field.equals("parameterIdentifier")){parameter.setParameterIdentifier(literal.getLiteral());}
                if(field.equals("questionParameter")){parameter.setQuestionParameter(literal.getLiteral());}
                if(field.equals("parameterDescription")){parameter.setParameterDescription(literal.getLiteral());}
                if(field.equals("unit")){parameter.setUnit(literal.getLiteral());}
            }
        }
        if(parameter.getNumeric()!=null
                //&&parameter.getParameterIdentifier()!=null
                &&parameter.getQuestionParameter()!=null
                &&parameter.getUnit()!=null)
        {
            return parameter;
        }else{
            return null;
        }
    }


    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public OWLObjectRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(OWLObjectRenderer renderer) {
        this.renderer = renderer;
    }
    public OWLReasoner getReasoner() {
        return reasoner;
    }

    public void setReasoner(OWLReasoner reasoner) {
        this.reasoner = reasoner;
    }

    public OWLReasonerFactory getReasonerFactory() {
        return reasonerFactory;
    }

    public void setReasonerFactory(OWLReasonerFactory reasonerFactory) {
        this.reasonerFactory = reasonerFactory;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public OWLOntologyManager getManager() {
        return manager;
    }

    public void setManager(OWLOntologyManager manager) {
        this.manager = manager;
    }

    public OWLDataFactory getFactory() {
        return factory;
    }

    public void setFactory(OWLDataFactory factory) {
        this.factory = factory;
    }
    public static String parseIRI(String iri){
        Pattern pattern = Pattern.compile("#(.*?)>");
        Matcher matcher = pattern.matcher(iri);
        if (matcher.find()) {
            return (matcher.group(1));
        }
        return null;
    }

    public static String parseDateTime(String datetime){
        return datetime.replaceAll("T", " ");
    }
}
