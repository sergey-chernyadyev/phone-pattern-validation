package ru.itdt.opora.document.validation;

import java.util.List;

import ru.itdt.opora.document.Document;

public interface DocumentValidator {
	/**
	 * ��������� ��������� ���������� �� ���������
	 * 
	 * @return ValidationResult - ������, ���������� ������ �������, �� ���������
	 *         ��������
	 */
	ValidationResult validate(Document doc) throws Exception;

	/**
	 * �������� ������ �������, �� ��������� ���������
	 */
	public static class ValidationResult {
		public static final String INVALID_MESSAGE_HEADER = "��������! ������������ �������� � �������:";
		private List<InvalidData> invalidData;

		/**
		 * ������ ����������� ���������� ��� ������� ������ ���������
		 * 
		 * @param data - ������ ������ ���������, ���������� ��� ������, ������ �
		 *             �������� �������
		 */
		public ValidationResult(List<InvalidData> data) {
			invalidData = data;
		}

		/**
		 * ������ ����������� ���������� ��� ���������� ������ ���������
		 */
		public ValidationResult() {
			invalidData = null;
		}

		/**
		 * @return ������ � ���������� ������ ���� null ���� ��� ������ ���� �����
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
