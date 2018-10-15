package ru.itdt.opora.document.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.itdt.opora.document.Document;
import ru.itdt.opora.document.FieldSet;
import ru.itdt.opora.document.validation.DocumentValidator.ValidationResult.InvalidData;

/**
 * 
 * ��������� �������� ���� PhoneNumber � ��������� �� ������������ �������
 * ������������ ������� ������� � ������ � ��������:
 * <ul>
 * <li>8-xxx-xxx-xx-xx (��� ���� ������ ����������� �� ���� ��������)</li>
 * <li>8(xxx)-xxx-xx-xx (������ ���� ����������� �� ��������� ��������, ������
 * ������ �������������� � ����� ������)</li>
 * </ul>
 */
public class PhoneValidator implements DocumentValidator {
	public static final String PATTERN_STR = "8(?:(?:[\\(](\\d{3})[\\)])|(?:[-]?(\\d{3})))[-]?(\\d{3})[-]?(\\d{2})[-]?(\\d{2})";
	public static final Pattern PATTERN = Pattern.compile(PATTERN_STR);
	public static final String PHONE_NUMBER = "PhoneNumber";
	public static final String FIELD_SET = "USERTABLE";

	public ValidationResult validate(Document doc) {
		boolean isValid = true;
		List<InvalidData> invalidData = new ArrayList<InvalidData>();
		{
			int index = 0;
			for (FieldSet table : doc.getFieldSet(FIELD_SET)) {
				Object obj = table.getField(PHONE_NUMBER);
				Objects.requireNonNull(obj, "���� \"" + PHONE_NUMBER + "\" �� ������ ���� ����� null");
				String phoneNumber = table.getField(PHONE_NUMBER).toString();
				Matcher match = PATTERN.matcher(phoneNumber);
				if (!match.matches()) {
					isValid = false;
					invalidData.add(new InvalidData(PHONE_NUMBER, index, (String) obj));
				}

				index++;
			}
		}
		if (isValid) {
			return new ValidationResult();
		} else {
			return new ValidationResult(invalidData);
		}
	}

}
