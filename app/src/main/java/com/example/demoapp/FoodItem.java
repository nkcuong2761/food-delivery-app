/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
 * Section: Section 2
 * Date: 4/3/2021
 * Time: 12:27 PM
 *
 * Project: DemoApp
 * Package: com.example.demoapp
 * Class: FoodItem
 *
 * Description:
 *
 * ****************************************
 */
package com.example.demoapp;

public class FoodItem {
	private String image;
	private String name;
	private float price;
	private String description;
	private String longDescription;

	public FoodItem(String image, String name, float price, String description, String longDescription) {
		this.image = image;
		this.name = name;
		this.price = price;
		this.description = description;
		this.longDescription = longDescription;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
}
