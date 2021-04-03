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
	private String price;
	private String description;

	public FoodItem(String image, String name, String price, String description) {
		this.image = image;
		this.name = name;
		this.price = price;
		this.description = description;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
