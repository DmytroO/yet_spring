package com.yet.spring.core.loggers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.yet.spring.core.beans.Event;

public class CacheFileEventLogger implements EventLogger {

	private int cacheSize;
	private List<Event> cache;
	private FileEventLogger fileEventLogger;

	public CacheFileEventLogger(FileEventLogger fileEventLogger, int cacheSize) {
		this.fileEventLogger = fileEventLogger;
		this.cacheSize = cacheSize;
		this.cache = new ArrayList<Event>(cacheSize);
	}
	
	public void destroy() {
		if ( ! cache.isEmpty()) {
			writeEventsFromCache();
		}
	}

	@Override
	public void logEvent(Event event) {
		cache.add(event);
		
		if (cache.size() == cacheSize) {
			writeEventsFromCache();
			cache.clear();
		}
	}

	private void writeEventsFromCache() {
	    cache.stream().forEach(fileEventLogger::logEvent);
	}

}
