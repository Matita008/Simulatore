# 📚 CPU Simulator Documentation System

## 🎯 Overview

This guide covers the automated documentation generation system for the CPU Simulator project, including CI/CD pipelines, local development, and maintenance procedures.

## 🚀 Automated CI/CD Pipeline

### GitHub Actions Workflows

#### 1. Documentation Generation (`generate-docs.yml`)
**Triggers:**
- Push to `main` branch
- Pull requests to `main` branch  
- Manual workflow dispatch

**Features:**
- Generates professional Javadoc HTML documentation
- Deploys to GitHub Pages automatically
- Creates downloadable artifacts
- Uses Java 17 with Gradle build system

#### 2. Documentation Quality Check (`doc-quality-check.yml`)
**Triggers:**
- Push to `main`, `develop`, or `testing_coderabbit` branches
- Pull requests to `main` or `develop` branches

**Quality Metrics:**
- Coverage analysis and reporting
- Build validation
- Package documentation checks

## 🛠️ Local Development

### Gradle Tasks

```bash
# Generate documentation and open in browser
./gradlew generateAndOpenDocs

# Generate documentation only
./gradlew javadoc

# Check documentation coverage
./gradlew checkDocCoverage

# Build project with documentation
./gradlew build
