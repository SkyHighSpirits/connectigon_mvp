package com.example.connectigon_mvp.services;

import org.springframework.stereotype.Service;
import java.math.*;
public class DummyDataService {

    String[] titles = {
            "Writer",
            "Entrepreneur",
            "Engineer",
            "Artist",
            "Scientist",
            "Designer",
            "Innovator",
            "Philosopher",
            "Photographer",
            "Lawyer"
    };
    String[] descriptions = {
            "Crafting compelling narratives and evoking emotions through the power of words.",
            "Building businesses from scratch, turning ideas into reality, and embracing innovation.",
            "Solving complex problems with creativity and precision, designing solutions that shape the world.",
            "Expressing creativity through various mediums, capturing beauty, emotions, and ideas.",
            "Exploring the unknown, conducting experiments, and pushing the boundaries of knowledge.",
            "Creating visually stunning and functional designs, blending aesthetics with practicality.",
            "Pioneering new ideas, disrupting industries, and driving positive change through ingenuity.",
            "Contemplating the nature of existence, seeking wisdom, and questioning the fundamental aspects of life.",
            "Capturing moments in time, telling stories through images, and revealing the beauty of the world.",
            "Advocating for justice, navigating legal complexities, and upholding the rule of law."
    };

    String[] ages = {
            "20 years old",
            "32 years old",
            "45 years old",
            "28 years old",
            "37 years old",
            "50 years old",
            "22 years old",
            "39 years old",
            "42 years old",
            "26 years old"
    };

    public String getChosenDescription() {
        return chosenDescription;
    }

    public void setChosenDescription(String chosenDescription) {
        this.chosenDescription = chosenDescription;
    }

    public String getChosenTitle() {
        return chosenTitle;
    }

    public void setChosenTitle(String chosenTitle) {
        this.chosenTitle = chosenTitle;
    }

    public String getChosenAge() {
        return chosenAge;
    }

    public void setChosenAge(String chosenAge) {
        this.chosenAge = chosenAge;
    }

    String chosenDescription = "";
    String chosenTitle = "";
    String chosenAge = "";

    public DummyDataService()
    {
        int randomNumber = (int) (Math.random() * 10);
        int randomAge = (int) (Math.random() * 10);
        this.chosenDescription = createDescription(randomNumber);
        this.chosenTitle = createTitle(randomNumber);
        this.chosenAge = createAge(randomAge);

    }

    public String createDescription(int index)
    {
        return descriptions[index];

    }

    public String createAge(int index)
    {
        return ages[index];
    }

    public String createTitle(int index)
    {
        return titles[index];
    }

}
