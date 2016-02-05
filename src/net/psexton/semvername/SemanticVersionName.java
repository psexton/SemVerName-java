/*
 * SemanticVersionName.java, part of the semvername-java project
 * Created on Feb 1, 2016, 5:32:56 PM
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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author PSexton
 */
public class SemanticVersionName implements Comparable<SemanticVersionName> {
    private final String name;
    private final SemanticVersion semver;
    
    public static SemanticVersionName valueOf(String semVerNameString) {
        if(semVerNameString == null || semVerNameString.isEmpty())
            throw new IllegalArgumentException("string cannot be null or empty");
        
        // First use regex to break into name/major/minor/patch/prerelease parts
        Pattern pattern = Pattern.compile("([\\w-]+)-((\\d+)\\.(\\d+)\\.(\\d+)(-(.+))?)");
        Matcher matcher = pattern.matcher(semVerNameString);
        if(!matcher.matches())
            throw new IllegalArgumentException("string did not match regex");
        
        String nameCapture = matcher.group(1);
        String semverCapture = matcher.group(2);
        
        return new SemanticVersionName(nameCapture, SemanticVersion.valueOf(semverCapture));
    }
    
    /**
     * Returns an "empty" semvername of untitled-0.0.0.
     */
    public SemanticVersionName() {
        this("untitled", new SemanticVersion(0, 0, 0));
    }
    
    public SemanticVersionName(String name, SemanticVersion version) {
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("name cannot be null or empty");
        if(version == null)
            throw new IllegalArgumentException("version cannot be null");
        
        // Validate name
        Pattern p = Pattern.compile("[^a-zA-Z0-9-_]");
        if(p.matcher(name).find())
            throw new IllegalArgumentException("name string is restricted to alphanumerics, hyphens, and underscores");
        
        this.name = name;
        this.semver = version;
    }

    public SemanticVersionName(String name, Integer major, Integer minor, Integer patch) {
        this(name, new SemanticVersion(major, minor, patch));
    }
    
    public SemanticVersionName(String name, Integer major, Integer minor, Integer patch, String prerelease) {
        this(name, new SemanticVersion(major, minor, patch, prerelease));
    }
    
    @Override
    public int compareTo(SemanticVersionName rhs) {
        // First compare name parts
        int compareNames = this.name.compareTo(rhs.name);
        if(compareNames != 0)
            return compareNames;
        
        // If those are equal, then compare semver parts
        return this.semver.compareTo(rhs.semver);
    }
    
    /**
     * Check for Compatibly Greater Than status.
     * @param rhs SemanticVersionName to compare to
     * @return True if this is compatibly greather than rhs
     */
    public boolean isCompatiblyGreaterThan(SemanticVersionName rhs) {
        // Names must be equal, and this.semver must be cgt to rhs.semver
        return name.equals(rhs.name) && semver.cgt(rhs.semver);
    }
    
    //
    // Subsequent methods are boilerplate
    //
    
    public String getName() {
        return name;
    }

    public SemanticVersion getSemver() {
        return semver;
    }
    
    public SemanticVersionName setName(String name) {
        return new SemanticVersionName(name, semver);
    }
    
    public SemanticVersionName setSemver(SemanticVersion semver) {
        return new SemanticVersionName(name, semver);
    }
    
    @Override
    public String toString() {
        return name + "-" + semver.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.semver);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SemanticVersionName other = (SemanticVersionName) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.semver, other.semver)) {
            return false;
        }
        return true;
    }
    
    /**
     * Shorthand for isCompatiblyGreaterThan
     * @param rhs SemanticVersionName to compare to
     * @return True if this is compatibly greather than rhs
     */
    public boolean cgt(SemanticVersionName rhs) {
        return isCompatiblyGreaterThan(rhs);
    }
}
