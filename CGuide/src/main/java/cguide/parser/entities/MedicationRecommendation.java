package cguide.parser.entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class MedicationRecommendation extends ClinicalAction {
            private String activeIngredient;
            private String dosage;
            private String pharmaceuticalForm;
            private String posology;

    public static MedicationRecommendation fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, MedicationRecommendation.class);
    }

    @Override
	public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getPosology() {
        return posology;
    }

    public void setPosology(String posology) {
        this.posology = posology;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public void setPharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
