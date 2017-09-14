package com.wingarden.nexus.converter;

import java.io.File;

import com.beust.jcommander.converters.FileConverter;

public class MyFileconverter extends FileConverter {
	@Override
	public File convert(String value) {
		if (null == value || "".equals(value)) {
			return null;
		}
		return super.convert(value);
	}
}
