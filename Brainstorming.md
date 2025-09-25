# What can be tested in such a football project?

## **API layer**
- **Availability**: does the API respond (status 200 OK)?
- **Data structure**: does the JSON contain the expected fields (e.g. `homeTeam`, `awayTeam`, `score`, `totalshots`, `possession`)?
- **Data freshness**: in a live match, does the score update if you query again?
- **Error handling**: what happens if you request with a wrong `eventId`, or when there are no live matches?

## **UI layer (Sofascore website)**
- **Score display**: is the current score visible?
- **Color logic**: if your team is leading → green background, if losing → red background.
- **Statistics**: are shots, possession, goals displayed correctly?
- **Responsiveness**: does the layout work properly in mobile view as well?

## **Integration (API + UI)**
- Do the API results match what the UI shows?
- If the API reports a goal, does the UI update accordingly?

## **Extra (Prediction / AI module)**
- Prediction calculation: does the model run correctly?
- Output validation: does the probability make sense (e.g. 0–100%)?
- Stability: does it handle invalid input gracefully?


# Brainstorm – possible final project titles
- *“Automated Testing of Live Football Match Statistics via Playwright”*;
- *“Testing Sports Data APIs and UI Integration Using Page Object Model”*;
- *“Visualization and Automated Testing of Real-Time Football Results with Playwright”*;
- *“Predictive Sports Result Testing: API and UI Validation in Playwright”*;
- *“Automated Testing Framework for Real-Time Sports Statistics Applications”*;


# Summary
- **What** you are testing (API, UI, integration, prediction).
- **Where** you are testing (Sofascore API, Sofascore web UI).
- **With what tools** (Playwright, JUnit, Page Object Model).
- **How** you are testing (assertions, color logic checks, JSON validation).