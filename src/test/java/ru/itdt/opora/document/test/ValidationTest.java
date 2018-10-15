package ru.itdt.opora.document.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import ru.itdt.opora.document.Document;
import ru.itdt.opora.document.FieldSet;
import ru.itdt.opora.document.validation.PhoneValidator;
import ru.itdt.opora.document.validation.DocumentValidator.ValidationResult;
import ru.itdt.opora.document.validation.DocumentValidator.ValidationResult.InvalidData;

public class ValidationTest {
	private static final PhoneValidator validator = new PhoneValidator();

	@Test
	public void testDefaultPattern() throws Exception {
		String[] phones = new String[] { "8(932)3216242", "89326216262", "8-932-621-62-62", "8-932-6216262" };
		ArrayList<FieldSet> fields = new ArrayList<FieldSet>();
		for (String phone : phones) {
			HashMap<String, Object> phoneMap = new HashMap<String, Object>();
			phoneMap.put("PhoneNumber", phone);
			fields.add(new FieldSet(phoneMap));
		}
		ValidationResult result = validator.validate(new Document(fields));
		assertTrue(result.isValid());
		assertEquals("Строки с сообщениями не совпали", "", result.getMessage());
	}

	@Test
	public void testIllegalValues() throws Exception {
		String[] phones = new String[] { "8-(932)3216242", "8932)3216242", "8-932-621-62-62", "8-932-6216262",
				"8-9326-216262" };
		ArrayList<FieldSet> fields = new ArrayList<FieldSet>();
		for (String phone : phones) {
			HashMap<String, Object> phoneMap = new HashMap<String, Object>();
			phoneMap.put("PhoneNumber", phone);
			fields.add(new FieldSet(phoneMap));
		}
		ValidationResult res = validator.validate(new Document(fields));
		assertFalse(res.isValid());
		assertArrayEquals(
				new InvalidData[] { new InvalidData(PhoneValidator.PHONE_NUMBER, 0, "8-(932)3216242"),
						new InvalidData(PhoneValidator.PHONE_NUMBER, 1, "8932)3216242"),
						new InvalidData(PhoneValidator.PHONE_NUMBER, 4, "8-9326-216262"), },
				res.getInvalidData().toArray(new InvalidData[0]));

		assertEquals("Строки с сообщениями не совпали",
				"Внимание! Некорректные значения в строках: 0 (PhoneNumber - \"8-(932)3216242\"), 1 (PhoneNumber - \"8932)3216242\"), 4 (PhoneNumber - \"8-9326-216262\"),",
				res.getMessage());
	}

}
