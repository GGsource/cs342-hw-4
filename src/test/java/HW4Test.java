import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HW4Test {
	private static final HashSet<String> directoryContents = new HashSet<>();

	@BeforeAll
	static void setUp() {
		directoryContents.add("classified contents");
		directoryContents.add("magazine contents");
		directoryContents.add("debriefing on classified info...");
		directoryContents.add("helpful info brochure!");
		directoryContents.add("fun places to go on weekend!");
	}

	////UNRESTRICTED FILE FETCHER TESTS

	//Testing that unrestricted filefetcher can fetch all files without error
	@ParameterizedTest
	@ValueSource(strings = {"CLASSIFIED_document.txt", "ENTERTAINMENT_magazine.txt", "scp_brochure.txt", "debrief_classified.txt", "weekendentertainment.txt"})
	void unrestrictedFetcherThrowTest(String fileName) {
		FileFetcher fetcher = new UnrestrictedFetcher();
		//None of these should throw an error while fetching the file
		assertDoesNotThrow(()->{fetcher.fetchFile(fileName, true);}, "Oh no, fetching file threw an error when it wasn't supposed to!");
	}
	//Testing that unrestricted filefetcher returns the correct contents
	@ParameterizedTest
	@ValueSource(strings = {"CLASSIFIED_document.txt", "ENTERTAINMENT_magazine.txt", "scp_brochure.txt", "debrief_classified.txt", "weekendentertainment.txt"})
	void UnrestrictedFetcherContentTest(String fileName) {
		FileFetcher fetcher = new UnrestrictedFetcher();
		try {
			assertTrue(directoryContents.contains(fetcher.fetchFile(fileName, true)), "Contents of file " + fileName + " were not what we expected!");
		}
		catch (Exception er) {
			fail("Exception was thrown but shouldn't have!");
		}
	}

	////PROXY CIVILLIAN FILE FETCHER TESTS

	//Testing that error is thrown if file name has classified
	@ParameterizedTest
	@ValueSource(strings = {"CLASSIFIED_document.txt", "debrief_classified.txt"})
	void ProxyCivillianFetcherThrowsTest(String fileName) {
		FileFetcher fetcher = new ProxyCivillianFetcher();
		//None of these should throw an error while fetching the file
		assertThrows(Exception.class, ()->{fetcher.fetchFile(fileName, true);}, "Uh oh, ProxyCivillianFetcher was supposed to throw an exception but didn't!");
	}
	//Testing that no erro thrown if file name has entertainment or something else
	@ParameterizedTest
	@ValueSource(strings = {"ENTERTAINMENT_magazine.txt", "scp_brochure.txt", "weekendentertainment.txt"})
	void ProxyCivillianFetcherDoesNotThrowTest(String fileName) {
		FileFetcher fetcher = new ProxyCivillianFetcher();
		//None of these should throw an error while fetching the file
		assertDoesNotThrow(()->{fetcher.fetchFile(fileName, true);}, "ProxyCivillianFetcher threw but wasn't supposed to...");
	}
	//Testing contents returned are exactly correct
	@Test
	void ProxyCivillianFetcherContentTest() {
		FileFetcher fetcher = new ProxyCivillianFetcher();
		try {
			assertEquals("magazine contents", fetcher.fetchFile("ENTERTAINMENT_magazine.txt", true), "Contents returned by ProxyCivillianFetcher were not as expected!");
		}
		catch (Exception er) {
			fail("Exception was thrown when attempting to access file with ProxCvillian!");
		}
	}

	////PROXY OFFICIAL FILE FETCHER TESTS

	//Testing that error is thrown if file name has entertainment
	@ParameterizedTest
	@ValueSource(strings = {"ENTERTAINMENT_magazine.txt", "weekendentertainment.txt"})
	void ProxyOfficialFetcherThrowsTest(String fileName) {
		FileFetcher fetcher = new ProxyOfficialFetcher();
		//None of these should throw an error while fetching the file
		assertThrows(Exception.class, ()->{fetcher.fetchFile(fileName, true);}, "Uh oh, ProxyOfficialFetcher was supposed to throw an exception but didn't!");
	}
	//Testing that no erro thrown if file name has entertainment or something else
	@ParameterizedTest
	@ValueSource(strings = {"CLASSIFIED_document.txt", "debrief_classified.txt", "scp_brochure.txt"})
	void ProxyOfficialFetcherDoesNotThrowTest(String fileName) {
		FileFetcher fetcher = new ProxyOfficialFetcher();
		//None of these should throw an error while fetching the file
		assertDoesNotThrow(()->{fetcher.fetchFile(fileName, true);}, "ProxyOfficialFetcher threw but wasn't supposed to...");
	}
	//Testing contents returned by ProxyOfficialFetcher are as expected
	@Test
	void ProxyOfficialFetcherContentTest() {
		FileFetcher fetcher = new ProxyOfficialFetcher();
		try {
			assertEquals("debriefing on classified info...", fetcher.fetchFile("debrief_classified.txt", true), "Contents returned by ProxyCivillianFetcher were not as expected!");
		}
		catch (Exception er) {
			fail("Exception was thrown when attempting to access file with ProxOfficial!");
		}
	}

	//Ensure both Proxies can fetch files that are neither marked classified or entertainment
	@Test
	void ProxyContentComparisonTest() {
		FileFetcher fetcherCivillian = new ProxyCivillianFetcher();
		FileFetcher fetcherOfficial = new ProxyOfficialFetcher();
		try {
			assertEquals(fetcherCivillian.fetchFile("scp_brochure.txt", true), fetcherOfficial.fetchFile("scp_brochure.txt", true));
		} catch (Exception er) {
			fail("Contents returned from ProxyCivillianFetcher and ProxyOfficialFetcher should have been the same but were not...");
		}
	}
}
