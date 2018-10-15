package ru.itdt.opora.document.validation;

import java.util.List;

import ru.itdt.opora.document.Document;

public interface DocumentValidator {
	/**
	 * Проверяет введенную информацию из документа
	 * 
	 * @return ValidationResult - объект, содержащий список записей, не прошедших
	 *         проверку
	 */
	ValidationResult validate(Document doc) throws Exception;

	/**
	 * Содержит список записей, не прошедших валидацию
	 */
	public static class ValidationResult {
		public static final String INVALID_MESSAGE_HEADER = "Внимание! Некорректные значения в строках:";
		private List<InvalidData> invalidData;

		/**
		 * Данный конструктор вызывается при наличии ошибок валидации
		 * 
		 * @param data - список ошибок валидации, содержащий имя записи, индекс и
		 *             значение объекта
		 */
		public ValidationResult(List<InvalidData> data) {
			invalidData = data;
		}

		/**
		 * Данный конструктор вызывается при отсутствии ошибок валидации
		 */
		public ValidationResult() {
			invalidData = null;
		}

		/**
		 * @return список с ошибочными полями либо null если все данные были верны
		 */
		public List<InvalidData> getInvalidData() {
			return invalidData;
		}

		public boolean isValid() {
			return invalidData == null;
		}

		public String getMessage() {
			if (isValid()) {
				return "";
			} else {
				StringBuilder sb = new StringBuilder(INVALID_MESSAGE_HEADER);
				for (InvalidData data : invalidData) {
					sb.append(String.format(" %d (%s - \"%s\"),", data.getIndex(), data.getFieldName(),
							data.getValue().toString()));
				}
				return sb.toString();
			}
		}

		public static class InvalidData {
			private int index;
			private String fieldName;
			private Object value;

			public InvalidData(String dataType, int index, Object value) {
				this.index = index;
				this.value = value;
				this.fieldName = dataType;
			}

			public int getIndex() {
				return index;
			}

			public void setIndex(int index) {
				this.index = index;
			}

			public Object getValue() {
				return value;
			}

			public void setValue(Object value) {
				this.value = value;
			}

			public String getFieldName() {
				return fieldName;
			}

			public void setFieldName(String dataType) {
				this.fieldName = dataType;
			}

			@Override
			public boolean equals(Object other) {
				if (other instanceof InvalidData) {
					InvalidData otherObject = (InvalidData) other;

					return (otherObject.fieldName.equals(this.fieldName)) && (otherObject.index == index)
							&& ((otherObject.value == null && value == null) || (otherObject.value.equals(this.value)));
				}
				return false;
			}
		}
	}
}
