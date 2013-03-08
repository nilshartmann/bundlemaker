package org.bundlemaker.core.ui.validators;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * This class validates a String. It makes sure that the String is between 5 and 8 characters
 */
public class NonEmptyStringValidator implements IInputValidator {

  private final static NonEmptyStringValidator _instance = new NonEmptyStringValidator();

  public static NonEmptyStringValidator instance() {
    return _instance;
  }

  /**
   * you should use {@link #instance()} to get an instance of this class
   */
  public NonEmptyStringValidator() {
    //
  }

  /**
   * Validates the String. Returns null for no error, or an error message
   * 
   * @param newText
   *          the String to validate
   * @return String
   */
  @Override
  public String isValid(String newText) {
    if (newText == null || newText.trim().isEmpty()) {
      return "Enter a non-empty string";
    }

    // Input must be OK
    return null;
  }
}