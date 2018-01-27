package com.xinyunlian.jinfu.loan.enums;


import com.xinyunlian.jinfu.common.enums.PageEnum;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * 
 *
 */
public enum ETermType implements PageEnum {

	DAY("1", "天") {
		@Override
		public Date add(Date date, int termLen) {
			return DateUtils.addDays(date, termLen);
		}

		@Override
		public int getDay(int termLen) {
			return termLen;
		}

		@Override
		public int getWeek(int termLen) {
			return this.devide(termLen, 7);
		}

		@Override
		public int getMonth(int termLen) {
			return this.devide(termLen, 30);
		}

		@Override
		public int getYear(int termLen) {
			return this.devide(termLen, 360);
		}

		@Override
		public String getUnit() {
			return this.getText();
		}
	}, WEEK("2", "星期") {
		@Override
		public Date add(Date date, int termLen) {
			return DateUtils.addWeeks(date, termLen);
		}

		@Override
		public int getDay(int termLen) {
			return termLen * 7;
		}

		@Override
		public int getWeek(int termLen) {
			return termLen;
		}

		@Override
		public int getMonth(int termLen) {
			return this.devide(termLen * 7, 30);
		}

		@Override
		public int getYear(int termLen) {
			return this.devide(termLen * 7, 360);
		}

		@Override
		public String getUnit() {
			return "个" + this.getText();
		}
	}, MONTH("3", "月") {
		@Override
		public Date add(Date date, int termLen) {
			return DateUtils.addMonths(date, termLen);
		}

		@Override
		public int getDay(int termLen) {
			return termLen * 30;
		}

		@Override
		public int getWeek(int termLen) {
			return this.devide(termLen * 30, 7);
		}

		@Override
		public int getMonth(int termLen) {
			return termLen;
		}

		@Override
		public int getYear(int termLen) {
			return this.devide(termLen, 12);
		}

		@Override
		public String getUnit() {
			return "个" + this.getText();
		}
	}, YEAR("4", "年") {
		@Override
		public Date add(Date date, int termLen) {
			return DateUtils.addYears(date, termLen);
		}

		@Override
		public int getDay(int termLen) {
			return termLen * 360;
		}

		@Override
		public int getWeek(int termLen) {
			return this.devide(termLen * 360, 7);
		}

		@Override
		public int getMonth(int termLen) {
			return termLen * 12;
		}

		@Override
		public int getYear(int termLen) {
			return termLen;
		}

		@Override
		public String getUnit() {
			return this.getText();
		}
	}, ALL("ALL", "全部") {
		@Override
		public Date add(Date date, int termLen) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getDay(int termLen) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getWeek(int termLen) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getMonth(int termLen) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getYear(int termLen) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getUnit() {
			throw new UnsupportedOperationException();
		}
	};

	private String code;

	private String text;

	ETermType(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public abstract Date add(Date date, int termLen);

	public abstract int getDay(int termLen);
	public abstract int getWeek(int termLen);
	public abstract int getMonth(int termLen);
	public abstract int getYear(int termLen);

	public abstract String getUnit();

	public int devide(int a, int b){
		return a%b == 0 ? a/b: a/b +1;
	}
}
