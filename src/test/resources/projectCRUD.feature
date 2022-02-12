Feature: Project
  Scenario: crud project

    Given i have an authentication token for Todo.ly
    When i send a POST request to https://todo.ly/api/projects.json
    """
    {
      "Content":"Cucumber",
      "Icon":"1"
    }
    """
    Then the response code should be 200
    And the schema should be project-expected-schema.json
    And the Content should be equal to Cucumber
    And save the attribute Id in ID_PROJ
    When i send a PUT request to https://todo.ly/api/projects/ID_PROJ.json
    """
    {
      "Content":"CucumberUPDATE"
    }
    """
    Then the response code should be 200
    And the schema should be project-expected-schema.json
    And the Content should be equal to CucumberUPDATE