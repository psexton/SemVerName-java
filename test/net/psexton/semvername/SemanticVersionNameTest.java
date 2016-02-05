/*
 * SemanticVersionNameTest.java, part of the semvername-java project
 * Created on Feb 3, 2016, 2:32:34 PM
 *
 * semvername-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * semvername-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with semvername-java. If not, see <http://www.gnu.org/licenses/>.
 */
package net.psexton.semvername;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author PSexton
 */
public class SemanticVersionNameTest {
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Empty is valid
     */
    @Test
    public void validDefault() {
        SemanticVersionName semvername = new SemanticVersionName();
        assertEquals("untitled", semvername.getName());
        assertEquals(new SemanticVersion(0, 0, 0), semvername.getSemver());
        assertEquals("untitled-0.0.0", semvername.toString());
    }
    
    /**
     * Valid ctr
     */
    @Test
    public void validCtr() {
        SemanticVersionName semvername = new SemanticVersionName("hello", new SemanticVersion(1, 2, 3));
        assertEquals("hello", semvername.getName());
        assertEquals(new SemanticVersion(1, 2, 3), semvername.getSemver());
        assertEquals("hello-1.2.3", semvername.toString());
    }
    
    /**
     * Valid ctr
     */
    @Test
    public void validCtrFlattened() {
        SemanticVersionName semvername = new SemanticVersionName("hello", 1, 2, 3, "alpha");
        assertEquals("hello", semvername.getName());
        assertEquals(new SemanticVersion(1, 2, 3, "alpha"), semvername.getSemver());
        assertEquals("hello-1.2.3-alpha", semvername.toString());
    }
    
    /**
     * Invalid, null name
     */
    @Test
    public void invalidCtrNullName() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = new SemanticVersionName(null, new SemanticVersion(1, 2, 3));
    }
    
    /**
     * Invalid, empty name
     */
    @Test
    public void invalidCtrEmptyName() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = new SemanticVersionName("", new SemanticVersion(1, 2, 3));
    }
    
    /**
     * Invalid, non alphanumeric non hyphen non underscore character
     */
    @Test
    public void invalidCtrInvalidName() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = new SemanticVersionName("product.subproduct", new SemanticVersion(1, 2, 3));
    }
    
    /**
     * Invalid, null semver
     */
    @Test
    public void invalidCtrNullSemver() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = new SemanticVersionName("hello", null);
    }
    
    
    /**
     * Valid valueOf
     */
    @Test
    public void validValueOf() {
        SemanticVersionName semvername = SemanticVersionName.valueOf("hello-1.2.3");
        assertEquals("hello", semvername.getName());
        assertEquals(new SemanticVersion(1, 2, 3), semvername.getSemver());
    }
    
    /**
     * Inalid valueOf, null
     */
    @Test
    public void invalidValueOfNull() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = SemanticVersionName.valueOf(null);
    }
    
    /**
     * Invalid valueOf, empty
     */
    @Test
    public void invalidValueOfEmpty() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = SemanticVersionName.valueOf("");
    }
    
    /**
     * Invalid valueOf, non alphanumeric non hyphen non underscore in name
     */
    @Test
    public void invalidValueOfBadName() {
        exception.expect(IllegalArgumentException.class);
        SemanticVersionName semvername = SemanticVersionName.valueOf("product.subproduct-1.2.3");
    }
    
    /**
     * Test that setter returns a new instance
     */
    @Test
    public void setMajor() {
        SemanticVersionName orig = new SemanticVersionName();
        SemanticVersionName modified = orig.setName("foo");
        assertNotSame(orig, modified);
    }
    
    /**
     * Test that setter returns a new instance
     */
    @Test
    public void setMinor() {
        SemanticVersionName orig = new SemanticVersionName();
        SemanticVersionName modified = orig.setSemver(new SemanticVersion(0, 0, 1));
        assertNotSame(orig, modified);
    }
    
    /**
     * Basic checking of hashcodes
     */
    @Test
    public void testHashCode() {
        SemanticVersionName versionName1a = new SemanticVersionName("foo", 1, 0, 0);
        SemanticVersionName versionName1b = new SemanticVersionName("foo", 1, 0, 0);
        SemanticVersionName versionName2 = new SemanticVersionName("bar", 1, 0, 0);
        
        assertEquals(versionName1a.hashCode(), versionName1b.hashCode());
        assertEquals(versionName1a.hashCode(), versionName1a.hashCode());
        assertFalse(versionName1a.hashCode() == versionName2.hashCode());
    }
}
