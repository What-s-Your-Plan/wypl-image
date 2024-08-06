package com.wypl.image.infrastructure;

import java.io.File;
import java.util.List;

public interface CloudStorageProvider {
	String fileUpload(final File file);

	void filesRemove(final List<String> fileNames);
}
