package Food;

public class FoodDTO {
	String foodName = null;
	String foodColor = null;
	String foodMainIngredient = null;
	String foodOriginCountry = null;
	int foodAvgCost = 0;
	
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getFoodColor() {
		return foodColor;
	}
	public void setFoodColor(String foodColor) {
		this.foodColor = foodColor;
	}
	public String getFoodMainIngredient() {
		return foodMainIngredient;
	}
	public void setFoodMainIngredient(String foodMainIngredient) {
		this.foodMainIngredient = foodMainIngredient;
	}
	public String getFoodOriginCountry() {
		return foodOriginCountry;
	}
	public void setFoodOriginCountry(String foodOriginCountry) {
		this.foodOriginCountry = foodOriginCountry;
	}
	public int getFoodAvgCost() {
		return foodAvgCost;
	}
	public void setFoodAvgCost(int foodAvgCost) {
		this.foodAvgCost = foodAvgCost;
	}
}
